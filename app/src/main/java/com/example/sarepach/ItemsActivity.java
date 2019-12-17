package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * This is a ItemsActivity class that shows all of the items up for auction along
 * with all of the information for each one
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class ItemsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        Spinner filter = (Spinner) findViewById(R.id.filter);
        filter.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Trending");
        categories.add("Price: Low to High");
        categories.add("Price: High to Low");
        categories.add("Home & Kitchen");
        categories.add("Clothing & Accessories");
        categories.add("Food & Drink");
        categories.add("Technology");
        categories.add("Travel & Vehicles");
        categories.add("Sports & Entertainment");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        filter.setAdapter(dataAdapter);

        String allItems = "";
        try {
            // When we open the items activity page we load all items in the database until the user wants to search / filter
            AsyncRetrieveBids asyncRetrieveBids = new AsyncRetrieveBids();
            allItems = asyncRetrieveBids.execute().get();
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            addItemEntitys(tableLayout, allItems);
        } catch(Exception e){
            Log.w("ItemsActivity", "Error loading all items: " +  e);
        }
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
    public void goDescription(View v, String itemName) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("ITEM_ID", itemName);
        this.startActivity(intent);
    }



    /**
     * Adds an item to a table to display the items the user as bidded on
     * for their profile page
     *
     * @param v
     *            the screen view
     * @param item
     *            the item being added
     */
    // will eventually parse through all items
    public void addItemEntitys(TableLayout v, String item){
        String[] items_to_display = item.split("_");
        for(int i = 0; i < items_to_display.length; i++){
            addTableRow(v, items_to_display[i]);
        }


    }

    /**
     * Adds a row to the table to display more items
     *
     * @param v
     *            the screen view
     * @param item
     *            the item being added
     */
    public void addTableRow(TableLayout v, String item) {
        try {
            // Add table row entry to the table layout
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 200));

            tableRow.setBackgroundColor(Color.parseColor("#DDDDDD"));

            //tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            v.addView(tableRow);
            // Add text view to the new row

            addImageView(tableRow, item);
            addButton(tableRow, item);
            addTextView(tableRow, item);


        }catch (Exception e) {
            Log.w("ProfileActivityTableRow", e);
        }
        //addButton();
        //ImageView imageView = new ImageView(getApplicationContext());
        //TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        //imageView.setLayoutParams(layoutParams);
    }

    /**
     * Adds a text view to allow more items to be displayed
     *
     * @param v
     *            the screen view
     * @param item
     *            the item being added
     */
    public void addTextView(TableRow v, String item){
        TextView textView = new TextView(getApplicationContext());
        //textView.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        //textView.setLayoutParams(layoutParams);
        textView.setTextSize(18f);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.parseColor("#DDDDDD"));
        //textView.c();

        String next_bid = "Next bid: \n$";
        textView.setText(next_bid + item.split(";")[2]);
        textView.setHeight(200);
        textView.setWidth(220);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);


        v.addView(textView);

    }

    /**
     * Adds a image view to allow more items to be displayed
     *
     * @param v
     *            the screen view
     * @param item
     *            the item being added
     */
    public void addImageView(TableRow v, String item){
        ImageView imageView = new ImageView(getApplicationContext());

        Picasso.get().load(item.split(";")[1]).into(imageView);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        v.addView(imageView, 200 ,200);
    }

    public void addButton(TableRow v, String item){
        Button button = new Button(this);
        //button.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        //button.setLayoutParams(layoutParams);
        button.setTextSize(24f);
        button.setTextColor(Color.BLACK);
        button.setBackgroundColor(Color.parseColor("#DDDDDD"));
        
        button.setText(item.split(";")[0]);
        button.setAllCaps(false);
        button.setHeight(200);
        button.setWidth(1000);
        button.setGravity(Gravity.LEFT);
        ItemListener itemListener = new ItemListener(item.split(";")[0]);
        button.setOnClickListener(itemListener);

        v.addView(button);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String filterChoice = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        if(filterChoice.equals("Trending")) {
            Toast.makeText(adapterView.getContext(), "HI", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected class ItemListener implements View.OnClickListener
    {

        String itemName;
        public ItemListener(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public void onClick(View v)
        {
            //read your lovely variable
            goDescription(v, this.itemName);
        }

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
        private final String validateUserPHP = "http://sarepach.cs.loyola.edu/UserConnection/displayItems.php";
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
                url = new URL(validateUserPHP );

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