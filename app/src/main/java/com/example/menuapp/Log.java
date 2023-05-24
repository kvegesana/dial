package com.example.menuapp;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

    public void append2(String userid, String text){
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy ");
        String date = df.format(Calendar.getInstance().getTime());
        String root = Environment.getExternalStorageDirectory().toString();
        File logDir = new File(root + "/logs/"+date+"/"+userid);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logAnalysis = new File(logDir + "/logAnalysis.txt");
        if (!logAnalysis.exists())
        {
            try
            {
                logAnalysis.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("IO exception-> logAnalysis file");

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logAnalysis, true));
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void append3(String userid, String text){
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy ");
        String date = df.format(Calendar.getInstance().getTime());
        String root = Environment.getExternalStorageDirectory().toString();
        File logDir = new File(root + "/logs/"+date+"/"+userid);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logAnalysis = new File(logDir + "/TalkbackLogAnalysis.txt");
        if (!logAnalysis.exists())
        {
            try
            {
                logAnalysis.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("IO exception-> logAnalysis file");

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logAnalysis, true));
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void append4(String userid, String text){
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy ");
        String date = df.format(Calendar.getInstance().getTime());
        String root = Environment.getExternalStorageDirectory().toString();
        File logDir = new File(root + "/logs/"+date+"/"+userid);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logAnalysis = new File(logDir + "/TBKeyboardLogAnalysis.txt");
        if (!logAnalysis.exists())
        {
            try
            {
                logAnalysis.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("IO exception-> logAnalysis file");

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logAnalysis, true));
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void append(String userId, String text) {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy ");
        String date = df.format(Calendar.getInstance().getTime());
        String root = Environment.getExternalStorageDirectory().toString();
        File logDir = new File(root + "/logs/"+date+"/"+userId);

        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logFile = new File(logDir + "/logFile.txt");

        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("IO exception-> Log file");

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
