package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.valueOf;

/**
 * This is a ItemsActivity class that shows all of the items up for auction along
 * with all of the information for each one
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class ItemsActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app that displays all of the items up for auction
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);




    }

    /**
     * Opens the app on the start
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that displays the profile pages of the user
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that shows the description of a single item
     *
     * @param v
     *            the screen view
     */
    public void goDescription(View v) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        this.startActivity(intent);
    }

    /**
     * This is a AsyncRetrieveProfileBids class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the validateUser php file in the server which checks if the username
     * and password combination of the user are correct.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncRetrieveBids extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String username;
        String password;
        HttpURLConnection conn;
        URL url = null;
        private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/displayProfileBids.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the username (email) of the user
         */
        public AsyncRetrieveBids(){
            this.username = "?email=" + MainActivity.currentUser.Email;
        }

        /**
         * This will interact with UI and display loading message
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();
        }

        /**
         * This connects with the validateUser php file on the server to check
         * that the email and password of the user is a correct combination in the
         * database table
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