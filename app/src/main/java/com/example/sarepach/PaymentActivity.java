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

public class PaymentActivity extends AppCompatActivity {

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
                            String result = asyncTask.execute().get();
                            AlertDialog alertDialog = new AlertDialog.Builder(PaymentActivity.this).create();
                            alertDialog.setTitle("Display Result");
                            alertDialog.setMessage(result);
                            alertDialog.show();
                            goShippingInfo(null);
                        } catch (Exception e) {
                            Log.w("PaymentAcitivity", e);

                        }

                    }
                });
    }


    public void onStart() {
        super.onStart();
    }


    public void returnToItems(View v) {
        Intent intent = new Intent(this, ItemsActivity.class);
        this.startActivity(intent);
    }

    public void goShippingInfo(View w) {
        Intent intent = new Intent(this, ShippingActivity.class);
        this.startActivity(intent);
    }

    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String nameIn;
        String numberIn;
        String expDateIn;
        String CSVIn;


        HttpURLConnection conn;
        URL url = null;
        private final String addPayment = "http://sarepach.cs.loyola.edu/UserConnection/addPayment.php";
        //private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/test.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        public AsyncRetrieve(String number, String expDate, String csv, String nameoncard){
            this.numberIn = "?creditCard=" + number;
            this.expDateIn = "&expirationDate="+  expDate;
            this.CSVIn = "&csv="+  csv;
            this.nameIn = "&name="+  nameoncard;


            Log.w("payment", this.addPayment);
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
                url = new URL(addPayment + this.numberIn + this.nameIn  + this.expDateIn + this.CSVIn + "&email=" + MainActivity.currentUser.Email );
                Log.w("payment Acitivity", url.toString());

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