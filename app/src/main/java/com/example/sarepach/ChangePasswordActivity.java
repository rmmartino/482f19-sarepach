package com.example.sarepach;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Button submitButton;
        final EditText emailText;
        final EditText oldPasswordText;
        final EditText newPasswordText;
        final EditText newPasswordConfText;

        submitButton = (Button)findViewById(R.id.submitID);
        oldPasswordText = (EditText)findViewById(R.id.oldpassID);
        newPasswordText = (EditText)findViewById(R.id.newpasswordID);
        newPasswordConfText = (EditText)findViewById(R.id.passwordConfID);

        // Want to wait for user to click login or sign up...
        submitButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view) {

                        if ((newPasswordText.getText().toString()).equals((newPasswordConfText.getText().toString()))) {
                            AsyncRetrieve asyncTask = new AsyncRetrieve(oldPasswordText.getText().toString(), newPasswordText.getText().toString(), newPasswordConfText.getText().toString());
                            try {
                                String result = asyncTask.execute().get();
                                AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                                alertDialog.setTitle("Display Result");
                                alertDialog.setMessage(result);
                                alertDialog.show();
                                //goProfile(null);
                            } catch (Exception e) {
                                Log.w("Change Pass", e);

                            }

                        }
                        else
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("New Password and Password Confirmation must match.");
                            alertDialog.show();
                        }
                    }
                });

    }


    public void onStart() {
        super.onStart();
    }

    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    public void goDescription(View v) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        this.startActivity(intent);
    }

    public void goSettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String oldPassIn;
        String newPassIn;

        HttpURLConnection conn;
        URL url = null;
        private final String changePass = "http://sarepach.cs.loyola.edu/UserConnection/changePassword.php";
        //private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/test.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        public AsyncRetrieve(String old, String newpass, String conf){
            this.oldPassIn = "?oldPassword=" + old;
            this.newPassIn = "&newPassword="+  newpass;


            Log.w("change pass", this.changePass);
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
                //url = new URL(addUser + this.username );
                url = new URL(changePass + this.oldPassIn  + this.newPassIn + "&email=" + MainActivity.currentUser.Email );
                Log.w("shipping Acitivity", url.toString());

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
                if (response_code == HttpURLConnection.HTTP_OK) {

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

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

    }

}
