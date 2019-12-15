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

public class ShippingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        Button signUpButton;
        final EditText nameOnCardText;
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
        nameOnCardText = (EditText)findViewById(R.id.nameInput);


        // Want to wait for user to click login or sign up...
        signUpButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                            AsyncRetrieve asyncTask = new AsyncRetrieve(houseText.getText().toString(), streetText.getText().toString().replaceAll("\\s","") , cityText.getText().toString().replaceAll("\\s","") , stateText.getText().toString() , zipcodeText.getText().toString() , nameOnCardText.getText().toString().replaceAll("\\s",""));
                            try {
                                String result = asyncTask.execute().get();
                                AlertDialog alertDialog = new AlertDialog.Builder(ShippingActivity.this).create();
                                alertDialog.setTitle("Display Result");
                                alertDialog.setMessage(result);
                                alertDialog.show();
                                //submit(null);
                            } catch (Exception e) {
                                Log.w("SignUpActivity", e);

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

    public void submit(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    protected class AsyncRetrieve extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String houseIn;
        String streetIn;
        String cityIn;
        String stateIn;
        String zipIn;
        String nameIn;

        HttpURLConnection conn;
        URL url = null;
        private final String addShipping = "http://sarepach.cs.loyola.edu/UserConnection/addShipping.php";
        //private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/test.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        public AsyncRetrieve(String house, String street, String city, String state, String zipcode, String name){
            this.houseIn = "?houseInput=" + house;
            this.streetIn = "&streetInput="+  street;
            this.cityIn = "&townInput="+  city;
            this.stateIn = "&stateInput="+  state;
            this.zipIn = "&zipcodeInput="+  zipcode;
            this.nameIn = "&nameOnCardInput="+  name;


            Log.w("signupActivity", this.addShipping);
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
                url = new URL(addShipping + this.houseIn  + this.streetIn + this.cityIn + this.stateIn + this.zipIn + this.nameIn + "&email=" + MainActivity.currentUser.Email );
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