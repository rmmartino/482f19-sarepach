/*
@author: Patrick & Sarah
 */

package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button signUpButton;
        final EditText usernameText;
        final EditText passwordText;
        final EditText passwordConfText;

        final EditText firstText;
        final EditText lastText;

        setContentView(R.layout.activity_signup);
        signUpButton = (Button)findViewById(R.id.signupID);
        usernameText = (EditText)findViewById(R.id.emailID);
        passwordText = (EditText)findViewById(R.id.passwordID);
        passwordConfText = (EditText)findViewById(R.id.passwordConfID);
        firstText = (EditText)findViewById(R.id.firstID);
        lastText = (EditText)findViewById(R.id.lastID);


        // Want to wait for user to click login or sign up...
        signUpButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if ((passwordText.getText().toString()).equals((passwordConfText.getText().toString()))) {
                            AsyncRetrieve asyncTask = new AsyncRetrieve(usernameText.getText().toString(), passwordText.getText().toString(), firstText.getText().toString(), lastText.getText().toString() );
                            try {
                                String result = asyncTask.execute().get();
                                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                                alertDialog.setTitle("Display Result");
                                alertDialog.setMessage(result);
                                alertDialog.show();
                                returnToItems(null);
                            } catch (Exception e) {
                                Log.w("SignUpActivity", e);

                            }
                        }
                        else
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Password and Password Confirmation must match.");
                            alertDialog.show();
                        }

                    }
                });
    }


    public void onStart() {
        super.onStart();
    }


    public void cancel(View w) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void returnToItems(View v) {
        Intent intent = new Intent(this, ItemsActivity.class);
        this.startActivity(intent);
    }

    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String username;
        String password;

        String first;
        String last;

        HttpURLConnection conn;
        URL url = null;
        private final String addUser = "http://sarepach.cs.loyola.edu/UserConnection/addUser.php";
        //private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/test.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        public AsyncRetrieve(String user, String pass, String first, String last){
            this.username = user;
            this.username = "?email=" + user;
            this.password = "&password="+  pass;
            this.first = "&firstName="+  first;
            this.last = "&lastName="+  last;

            Log.w("signupActivity", this.addUser);
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
                url = new URL(addUser + this.username  + this.password + this.first + this.last );
                Log.w("signupActivity", url.toString());

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