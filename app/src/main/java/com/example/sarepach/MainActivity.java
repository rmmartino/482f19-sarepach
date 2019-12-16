package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// Separate additions for AsyncTask connection to server
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.valueOf;

/**
 * This is a MainActivity class that corresponds with the first screen of the app that
 * includes the login and sign up of users and goes to the next screen based on users choice
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */

public class MainActivity extends AppCompatActivity {

    protected static User currentUser = null;

    /**
     * Creates the first screen of the app asking the user to sign in or sign up and
     * goes to the next screen based on users choice
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button loginButton;
        final EditText usernameText;
        final EditText passwordText;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button)findViewById(R.id.loginID);
        usernameText = (EditText)findViewById(R.id.emailID);
        passwordText = (EditText)findViewById(R.id.passwordID);
        // Want to wait for user to click login or sign up...
        loginButton.setOnClickListener(
                new View.OnClickListener()
                {
                    /**
                     * Sets up the screen that follows after the user clicks on a
                     * button on the first screen
                     *
                     * @param view
                     *            the screen view
                     */
                    public void onClick(View view)
                    {
                        AsyncValidateUserInfo asyncTask = new AsyncValidateUserInfo(usernameText.getText().toString(), passwordText.getText().toString());
                        try {
                            String result = asyncTask.execute().get();
                            if(result.equals("Success")) {
                                currentUser = new User(usernameText.getText().toString());
                                goProfile(null);
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Incorrect username/password");
                                alertDialog.show();
                            }
                        } catch(Exception e){
                            Log.w("MainActivity", e);

                    }

                    }
                });
    }

    /**
     * Opens the app on the start
     *
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that follows after the user enters their email and
     * password and then clicks the login button
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the signup button
     * on the first screen
     *
     * @param v
     *            the screen view
     */
    public void gosignup(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    /**
     * This is a AsyncValidateUserInfo class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This validates the email and password of the user to make sure they're a
     * correct combination in the database.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncValidateUserInfo extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String username;
        String password;
        HttpURLConnection conn;
        URL url = null;
        private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/validateUserInput.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the username and password of the user
         *
         * @param user
         *            the username (email) of the user
         * @param pass
         *            the password of the user
         */
        public AsyncValidateUserInfo(String user, String pass){
            this.username = "?usernameText=" + user;
            this.password = "&passwordText=" + pass;
        }

        /**
         * This will interact with UI and display loading message
         *
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        /**
         * This connects with the validateUserInput php file on the server to check
         * that the username (email) and password combination entered by the user
         * are in the database
         *
         * @param params
         *
         * @return the output from the php file if it connects successfully, "unsuccessful" if
         * it doesn't connect successfully
         */
        @Override
        public String doInBackground(String... params) {
            try {
                // Only testing admin code for now (will execute client code instead)
                url = new URL(validateUserPHP + this.username + this.password );

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
                Log.w("MainActivity" , valueOf(response_code));
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