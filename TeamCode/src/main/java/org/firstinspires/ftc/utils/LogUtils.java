package org.firstinspires.ftc.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class LogUtils {


    /** the type of logmessage, used for sorting in a spreadsheet editor*/
    public enum LogType {
        normal,
        warning,
        error
    };
    /**name of the FTC team, used in the file namesw */
    static public String teamname = "jesuit";
    FileOutputStream fOUT = null;
    /**the list of filewriters, can be seen as the list of logging channels */
    public static final HashMap<String, FileWriter> filewriters=new HashMap<>();

    public static final void flushLoggers() {
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
        //force all loggers to flush to disk
        for (FileWriter writer : filewriters.values()) {
            try {
                writer.flush();
                writer.close();
            }
            catch (Exception e) {

            }
        }
        filewriters.clear();
    }

    private static final FileWriter startLogging(String id)  {
        SimpleDateFormat format=new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SSS");
        String fileName = teamname + format.format(new Date()) + "_"+id+".csv";
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


    /**closes the filewriter given by the id, do this to make sure your data gets saved */
    public static void stopLogging(int id) {
        try {
            filewriters.remove(id).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** log your data to the filewriter given by the ID */
    public static void log(LogType type, String message, String id) {
        FileWriter writer=filewriters.get(id);
        if (writer==null) {
            writer=startLogging(id);
        }

        try {
            filewriters.get(id).write(type.toString() + "," + System.currentTimeMillis() + "," + message+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message, String id) {
        FileWriter writer=filewriters.get(id);
        boolean first=false;
        if (writer==null) {
            writer=startLogging(id);
            first=true;
        }

        try {
            writer.write( message+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (first) {
            try {
                writer.flush();
            }
            catch (Exception e) {

            }
        }

    }



}