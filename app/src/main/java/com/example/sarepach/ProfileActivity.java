package com.example.sarepach;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.valueOf;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            AsyncRetrieveProfileBids asyncTask = new AsyncRetrieveProfileBids();
            String result = asyncTask.execute().get();
            AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
            alertDialog.setTitle("Display Result");
            alertDialog.setMessage(result + MainActivity.currentUser.Email);
            alertDialog.show();
        }
        catch(Exception e){
            Log.w("MainActivity", e);
        }
    }


    public void onStart() {
        super.onStart();
    }

    public void returnToItems(View v) {
        Intent intent = new Intent(this, ItemsActivity.class);
        this.startActivity(intent);
    }
    public void goDescription(View v) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        this.startActivity(intent);
    }


    public void gosettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    public void sendEmail(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));}

    protected class AsyncRetrieveProfileBids extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String username;
        String password;
        HttpURLConnection conn;
        URL url = null;
        private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/displayProfileBids.php";
        //private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/test.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        public AsyncRetrieveProfileBids(){
            this.username = "?email=" + MainActivity.currentUser.Email;
        }

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        @Override
        public String doInBackground(String... params) {
            try {
                // Only testing admin code for now (will execute client code instead)
                url = new URL(validateUserPHP + this.username);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data from json file
                conn.setDoInput(true);
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                Log.w("ProfileActivity" , valueOf(response_code));
                if (response_code == HttpURLConnection.HTTP_OK ) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                }


                else {

                    return ("unsuccessful" + validateUserPHP + this.username);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }
/**
 @Override
 public void onPostExecute(String result){
 AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
 alertDialog.setTitle("Display Result");
 alertDialog.setMessage(result);
 alertDialog.show();
 }
 */


    }


}

