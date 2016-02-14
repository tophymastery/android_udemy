package com.example.tophy.top10downloadapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstance();
    }

    private void initInstance() {
        String url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
        DownloadData downloadData = new DownloadData();
        downloadData.execute(url);
    }

    public class DownloadData extends AsyncTask<String, Void, String> {

        private String fileContents;

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadXMLFile(params[0]);
            if (fileContents == null) {
                Log.d("DownloadData", "Error downloading");
            }

            return fileContents;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("DownloadData", "Result was: " + s);
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();
            String result;
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", "response code is " + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int counter = 0;
                int charRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    String tmp = String.copyValueOf(inputBuffer);
                    Log.d("DownloadData", "read = " + tmp);
                    tempBuffer.append(tmp);
                }

                Log.d("DownloadData", "tempBuffer: " + tempBuffer.toString());
                return tempBuffer.toString();

            } catch (IOException e) {
                Log.e("DownloadData", "IOException reading data" + e.getMessage());
            } catch (SecurityException e) {
                Log.e("DownloadData", "Security Exception: " + e.getMessage());
            }

            return null;
        }
    }
}
