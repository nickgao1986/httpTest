package com.pic.optimize.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

    public static Bitmap loadImage(String sendUrl) {
        try {
            URL url = new URL(sendUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();


            String fileName = String.valueOf(System.currentTimeMillis());
            FileOutputStream outputStream = null;
            File fileDownload = null;
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File parent = Environment.getExternalStorageDirectory();
                fileDownload = new File(parent,fileName);
                outputStream = new FileOutputStream(fileDownload);
            }

            byte[] bytes = new byte[2*1024];
            int lens;
            if(outputStream != null) {
                while ((lens = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes,0,lens);
                }
            }

            return BitmapFactory.decodeFile(fileDownload.getAbsolutePath());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
