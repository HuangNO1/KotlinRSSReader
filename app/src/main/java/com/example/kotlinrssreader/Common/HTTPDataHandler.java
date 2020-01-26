package com.example.kotlinrssreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

public class HTTPDataHandler {
    static String stream="";

    public  HTTPDataHandler(){}

    public String GetHttpDataHandler(String urlString)
    {
        try{
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = r.readLine()) != null)
                    sb.append(line);
                    stream = sb.toString();
                    urlConnection.disconnect();
            }
        } catch (Exception ex)
        {
            return null;
        }
        return stream;
    }
}
