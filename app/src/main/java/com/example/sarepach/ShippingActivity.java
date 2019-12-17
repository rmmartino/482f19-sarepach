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

/**
 * This is a ShippingActivity class that corresponds with the screen of the app that
 * allows the user to enter their shipping information (their address) when they bid on an item
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class ShippingActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app that allows the user to put in their
     * shipping information
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        Button signUpButton;
        final EditText nameShippingText;
        final EditText houseText;
        final EditText streetText;
        final EditText cityText;
        final EditText stateText;
        final EditText zipcodeText;
        signUpButton = (Button)findViewById(R.id.submitID);
        houseText = (EditText)findViewById(R.id.houseInput);
        streetText = (EditText)findViewById(R.id.streetInput);
        cityText = (EditText)findViewById(R.id.cityInput);
        stateText = (EditText)findViewById(R.id.stateInput);
        zipcodeText = (EditText)findViewById(R.id.zipInput);
        nameShippingText = (EditText)findViewById(R.id.nameInput);


        // Want to wait for user to click login or sign up...
        signUpButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                            AsyncRetrieve asyncTask = new AsyncRetrieve(houseText.getText().toString(), streetText.getText().toString().replaceAll("\\s","") , cityText.getText().toString().replaceAll("\\s","") , stateText.getText().toString() , zipcodeText.getText().toString() , nameShippingText.getText().toString().replaceAll("\\s",""));
                            try {
                                submit(null);
                            } catch (Exception e) {
                                Log.w("SignUpActivity", e);

                            }
                    }
                });
    }

    /**
     * Opens the app on the start
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that displays all of the items available to bid on
     *
     * @param v
     *            the screen view
     */
    public void returnToItems(View v) {
        Intent intent = new Intent(this, ItemsActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen after the user clicks the 'submit' button after inputting
     * their shipping information which brings them back to their profile page
     *
     * @param v
     *            the screen view
     */
    public void submit(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * This is a AsyncRetrieve class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the addShipping php file in the server which adds the shipping
     * information into the database table with the corresponding user.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        String houseIn;
        String streetIn;
        String cityIn;
        String stateIn;
        String zipIn;
        String nameIn;

        HttpURLConnection conn;
        URL url = null;
        private final String addShipping = "http://sarepach.cs.loyola.edu/UserConnection/addShipping.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the username and password of the user
         *
         * @param house
         *            the house number for where the item will be shipped to
         * @param street
         *            the street name for where the item will be shipped to
         * @param city
         *            the city for where the item will be shipped to
         * @param state
         *            the state for where the item will be shipped to
         * @param zipcode
         *            the zipcode for where the item will be shipped to
         * @param name
         *            the name of the person for where the item will be shipped to
         */
        public AsyncRetrieve(String house, String street, String city, String state, String zipcode, String name){
            this.houseIn = "?houseInput=" + house;
            this.streetIn = "&streetInput="+  street;
            this.cityIn = "&townInput="+  city;
            this.stateIn = "&stateInput="+  state;
            this.zipIn = "&zipcodeInput="+  zipcode;
            this.nameIn = "&nameShippingInput="+  name;

            Log.w("signupActivity", this.addShipping);
        }

        /**
         * This will interact with UI and display loading message
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * This connects with the addShipping php file on the server to add the shipping
         * information to the database table in the row with the corresponding user.
         *
         * @param params
         *
         * @return the output from the php file if it connects successfully, "unsuccessful" if
         * it doesn't connect successfully
         */
        @Override
        public String doInBackground(String... params) {
            try {
                url = new URL(addShipping + this.houseIn  + this.streetIn + this.cityIn + this.stateIn + this.zipIn + this.nameIn + "&email=" + MainActivity.currentUser.Email );
                Log.w("Shipping Activity", url.toString());
            }

            catch (MalformedURLException e) {
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