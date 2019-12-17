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
 * This is a PaymentActivity class that corresponds with the screen of the app that
 * has the user input their payment information after bidding on an item
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class PaymentActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app asking the user for their payment information
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button submitButton;
        final EditText nameOnCardText;
        final EditText creditNumberText;
        final EditText expirationDateText;
        final EditText CSVText;

        submitButton = (Button)findViewById(R.id.submitID);
        nameOnCardText = (EditText)findViewById(R.id.creditNameInput);
        creditNumberText = (EditText)findViewById(R.id.creditNumberInput);
        String creditNumber;
        creditNumber = creditNumberText.getText().toString();
        if(!checkLuhn(creditNumber)) {
            AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this).create();
            alertDialog.setTitle("Not a valid credit card number");
            alertDialog.show();
        }
        expirationDateText = (EditText)findViewById(R.id.expirationDateInput);
        CSVText = (EditText)findViewById(R.id.CVVInput);

        // Want to wait for user to click login or sign up...
        submitButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                        AsyncRetrieve asyncTask = new AsyncRetrieve(creditNumberText.getText().toString().replaceAll("-","") ,expirationDateText.getText().toString().replaceAll("/",""),  CSVText.getText().toString() , nameOnCardText.getText().toString().replaceAll("\\s",""));
                        try {
                            goShippingInfo(null);
                        } catch (Exception e) {
                            Log.w("PaymentActivity", e);
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
     * Sets up the screen with the items on it
     *
     * @param v
     *            the screen view
     */
    public void returnToItems(View v) {
        Intent intent = new Intent(this, ItemsActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that asks the user for their shipping information
     *
     * @param v
     *            the screen view
     */
    public void goShippingInfo(View v) {
        Intent intent = new Intent(this, ShippingActivity.class);
        this.startActivity(intent);
    }

    /**
     * Checks to see if the credit card inputted by the user is valid
     *
     * @param cardNo
     *            the credit card number inputted by the user
     *
     * @return true if the credit card number is valid, false otherwise
     */
    static boolean checkLuhn(String cardNo)
    {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = cardNo.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }


    /**
     * This is a AsyncRetrieve class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the addPayment php file in the server which retrieves the
     * payment information inputted by the user on the app and puts it into the
     * database table in the row corresponding to the specific user
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        String nameIn;
        String numberIn;
        String expDateIn;
        String CSVIn;


        HttpURLConnection conn;
        URL url = null;
        private final String addPayment = "http://sarepach.cs.loyola.edu/UserConnection/addPayment.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the credit card number, expiration date, csv, and name on the card
         *
         * @param number
         *            the number of the credit card
         * @param expDate
         *            the expiration date of the credit card
         * @param csv
         *            the csv of the credit card
         * @param nameoncard
         *            the name on the credit card
         */
        public AsyncRetrieve(String number, String expDate, String csv, String nameoncard){
            this.numberIn = "?creditCard=" + number;
            this.expDateIn = "&expirationDate="+  expDate;
            this.CSVIn = "&csv="+  csv;
            this.nameIn = "&name="+  nameoncard;


            Log.w("payment", this.addPayment);
        }

        /**
         * This will interact with UI and display loading message
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * This connects with the addPayment php file on the server to add the credit
         * card information to the database table in the row of the corresponding user
         *
         * @param params
         *
         * @return the output from the php file if it connects successfully, "unsuccessful" if
         * it doesn't connect successfully
         */
        @Override
        public String doInBackground(String... params) {
            try {
                url = new URL(addPayment + this.numberIn + this.nameIn  + this.expDateIn + this.CSVIn + "&email=" + MainActivity.currentUser.Email );
                Log.w("Payment Activity", url.toString());

            } catch (MalformedURLException e) {
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