package org.firstinspires.ftc.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;


public class LogUtils {


    /** the type of logmessage, used for sorting in a spreadsheet editor*/
    public enum LogType {
        normal,
        warning,
        error
    }
    
    /**name of the FTC team, used in the file names */
    
    private static final String teamname = "jesuit"; 
    
    /**the list of filewriters, can be seen as the list of logging channels */
    private static final HashMap<String, FileWriter> filewriters=new HashMap<>();
    /**the list of pending records to be written to disk */
    private static final LinkedList<LogRecord> logRecords=new LinkedList<LogRecord>();
    
    private static final Thread writerThread;
    
    static {
    	//start the background thread that is responsible for writing to disk
    	writerThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				runThread();
			}
		});
    	writerThread.setDaemon(true);
    	writerThread.start();
    }
    
    private static final void runThread() {
    	while (true) {
    		LogRecord record=null;
    		synchronized (filewriters) {
    		    record=logRecords.poll();
            }
    		if (record!=null) {
    			try {
	    			FileWriter writer=filewriters.get(record.id);
	    	        if (writer==null) {
	    	            writer=startLogging(record.id);
	    	        }
	    	        writer.write(record.message+"\n");
    			}
    			catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		else {
    			try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
    		}
    		
    	}
    }

    public static void reset() {
        synchronized (filewriters) {
            filewriters.clear();
            logRecords.clear();
        }
    }
    
    public static final void flushLoggers() {
    	waitForRecords();
        //force all loggers to flush to disk
        for (FileWriter writer : filewriters.values()) {
            try {
                writer.flush();
            }
            catch (Exception e) {

            }
        }
    }

    public static final void closeLoggers() {
    	waitForRecords();
    	synchronized (filewriters) {
            //force all loggers to flush to disk and close
            for (FileWriter writer : filewriters.values()) {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception e) {

                }
            }
            filewriters.clear();
        }
    }

	private static void waitForRecords() {
            int count = 0;
            boolean empty=false;
            synchronized (filewriters) {
                empty= logRecords.isEmpty();
            }
            //waits up to 250 ms for log records to be empty
            while (!empty && count < 250) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                count++;
                synchronized (filewriters) {
                    empty= logRecords.isEmpty();
                }
            }

	}

    private static final FileWriter startLogging(String id)  {
        SimpleDateFormat format=new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss_SSS", Locale.US);
        String fileName = teamname +"_"+ format.format(new Date()) + "_"+id+".csv";
        File file = new File(getPublicAlbumStorageDir(teamname),fileName );

        try {
            FileWriter writer=new FileWriter(file);
            filewriters.put(id, writer);
            return writer;
        }
        catch (IOException e) {
            throw new RuntimeException("Error creating file writer",e);
        }
    }


    /**gets the public image folder */
    public static File getPublicAlbumStorageDir(String albumName) {
    	// Get the directory for the user’s public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        file.mkdirs();
        return file;
    }

    /** log your data to the filewriter given by the ID */
    public static void log(LogType type, String message, String id) {
        String messageLine=type.toString() + "," + System.currentTimeMillis() + "," + message;
        synchronized (filewriters) {
            logRecords.add(new LogRecord(id, messageLine));
        }
    }

    /** log your data to the filewriter given by the ID */
    public static void log(String message, String id) {
        synchronized (filewriters) {
            logRecords.add(new LogRecord(id, message));
        }
    }
    

    private static class LogRecord {
    	public LogRecord(String id2, String messageLine) {
			this.id=id2;
			this.message=messageLine;
		}
		public final String id;
    	public final String message;
    }
    



}
