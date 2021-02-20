package com.example.smt_kanban_android.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class JimiLogUtil {
    private static JimiLogUtil utils = null;
    private static final boolean DEBUG = true;
    private static final int MAX_LOG_FILE_COUNT = 5;
    private static final String LOG_PATH = "/sdcard/youthope4G/rawData/jmData/log/";
    private static final String CRASH_PATH = "/sdcard/youthope4G/rawData/jmData/crash/";

    public static JimiLogUtil getIntance() {
        if (utils == null) {
            synchronized (JimiLogUtil.class) {
                if (utils == null) {
                    utils = new JimiLogUtil();
                }
            }
        }
        return utils;
    }

    @SuppressLint("DefaultLocale")
    private static String getTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getLogDatetime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String str = formatter.format(new Date());
        return str;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getLogFileName() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(new Date());
        return str;
    }

    public static void saveLogFile(String content) {
        saveLogFile(getTag(),content);
    }

    public static void saveLogFile(Throwable ex) {
        if(null != ex) {
            StackTraceElement[] stes = ex.getStackTrace();
            StringBuilder sb = new StringBuilder("");
            for (StackTraceElement ste : stes)
                sb.append(ste.toString() + "\n");
            saveLogFile(sb.toString());
        }
    }

    public static void saveLogFile(String tag, String content) {
        if (DEBUG) {
            Log.d(tag,content);
            try {
                File logFile = new File(LOG_PATH);
                if (!logFile.exists()) {
                    logFile.mkdirs();
                }
                String strName = LOG_PATH + "Log_Senviv_"
                        + getLogFileName() + ".txt";
                RandomAccessFile randomFile = new RandomAccessFile(strName,
                        "rw");
                long fileLength = randomFile.length();
                randomFile.seek(fileLength);
                randomFile.write((getLogDatetime() + ":" + tag + "--"
                        + content + "\r\n").getBytes());
                randomFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startLogSizeCheck() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                checkDeleteFile(LOG_PATH);
                checkDeleteFile(CRASH_PATH);
            }
        }).start();
    }

    private void checkDeleteFile(String path) {
        File logFiles = new File(path);
        if (!logFiles.exists() || !logFiles.isDirectory()) {
            return;
        }
        if (!logFiles.canRead() || logFiles.list().length == 0) {
            return;
        }
        while (logFiles.listFiles().length > MAX_LOG_FILE_COUNT) {
            File[] listFile = logFiles.listFiles();
            List<File> delFiles = new ArrayList<File>();
            for (int i = 0; i < listFile.length; i++) {
                if(listFile[i].isDirectory()) {
                    File[] childList = listFile[i].listFiles();
                    if(childList.length > 0 ) {
                        for (int j = 0; j < listFile.length; j++) {
                            delFiles.add(childList[i]);
                        }
                    }
                }else {
                    delFiles.add(listFile[i]);
                }

            }
            Collections.sort(delFiles, new ComparatorByLastModified());
            String delPath = delFiles.get(0).getAbsolutePath();
            if (delPath.contains(getLogFileName())) {
                delPath = delFiles.get(1).getAbsolutePath();
            }
            File file = new File(delPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    class ComparatorByLastModified implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            if (f1.isDirectory() && f2.isFile()) {
                return -1;
            }
            if (f1.isFile() && f2.isDirectory()) {
                return 1;
            }
            return f1.getName().compareTo(f2.getName());
        }
    }
}
