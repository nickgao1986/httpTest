package com.pic.optimize;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();

    public static String get(final String strUrl) {
        try {
            URL getUrl = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)getUrl.openConnection();
            conn.setReadTimeout(5000);

            int response = conn.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = conn.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }else{
                Log.d(TAG,"<<<<<response="+response);
                return null;
            }
            } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String dealResponseResult(InputStream stream) throws IOException{
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static void doDelete(String urlStr,String params){
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            conn.getOutputStream().write(params.getBytes());
            ds.flush();
            int response = conn.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = conn.getInputStream();
                Log.d(TAG,"<<<<<inptStream="+inptStream);
            }else{
                Log.d(TAG,"<<<<<response="+response);
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }

    }



    public static String post(final String strUrl, String params) {

        try {
            URL postUrl = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)postUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);

//            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
//            conn.getOutputStream().write(params.getBytes());


            //设置请求体的类型是文本类型
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            conn.setRequestProperty("Content-Length", String.valueOf(params.getBytes().length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(params.getBytes());

            int response = conn.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = conn.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }else{
                Log.d(TAG,"<<<<<response="+response);
                return null;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public static String put(final String strUrl, String params) {

        try {
            URL postUrl = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)postUrl.openConnection();
            conn.setRequestMethod("PUT");
            conn.setReadTimeout(5000);

            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            conn.getOutputStream().write(params.getBytes());

            int response = conn.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = conn.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }else{
                Log.d(TAG,"<<<<<response="+response);
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
