package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;
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
        categories.add("Home and Kitchen");
        categories.add("Clothing and Accessories");
        categories.add("Food and Drink");
        categories.add("Technology");
        categories.add("Travel and Vehicles");
        categories.add("Sports and Entertainment");

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

            v.addView(tableRow);
            // Add text view to the new row
            addImageView(tableRow, item);
            addButton(tableRow, item);
            addTextView(tableRow, item);


        }catch (Exception e) {
            Log.w("ProfileActivityTableRow", e);
        }
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
        textView.setTextSize(18f);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.parseColor("#DDDDDD"));

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

    /**
     * Adds a button to view/bid on the item
     *
     * @param v
     *            the screen view
     * @param item
     *            the item being added
     */
    public void addButton(TableRow v, String item){
        Button button = new Button(this);
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

    /**
     * Runs the AsyncRetrieveFilter class with runs SQL statements on the database table
     * based on the users chosen filter
     *
     * @param adapterView
     *            the AdapterView where the selection happened
     * @param view
     *            the screen view
     * @param i
     *            the position of the filter in the dropdown menu
     * @param l
     *            the row id of the filter that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String filterChoice = adapterView.getItemAtPosition(i).toString();
        String result;
        try {
            // Display the items associated with the users chosen filter
            AsyncRetrieveFilter asyncRetrieveFilter = new AsyncRetrieveFilter(filterChoice);
            result = asyncRetrieveFilter.execute().get();
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            addItemEntitys(tableLayout, result);
        } catch (Exception e) {
            Log.w("ItemsActivity", "Error loading all items: " + e);
        }
    }

    /**
     * Invoked when the selection disappears from the view
     *
     * @param adapterView
     *            the AdapterView that contains no selected item
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * This is a ItemListener class that puts a button on the name of each item
     * in the display list of items and then brings them to the information regarding
     * the selected item.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class ItemListener implements View.OnClickListener
    {
        String itemName;

        /**
         * Retrieves the name of the item selected
         *
         * @param itemName
         *            the name of the item clicked on
         */
        public ItemListener(String itemName) {
            this.itemName = itemName;
        }

        /**
         * Goes to the description page when the button (name of item) gets clicked
         *
         * @param v
         *          the screen view
         */
        @Override
        public void onClick(View v)
        {
            //read your lovely variable
            goDescription(v, this.itemName);
        }
    }

    /**
     * This is a AsyncRetrieveBids class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the displayItems php file in the server which displays all of
     * the items on the auction items page.
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
        private final String displayItemsPHP = "http://sarepach.cs.loyola.edu/UserConnection/displayItems.php";
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
        }

        /**
         * This connects with the displayItems php file on the server to display
         * all of the items in the database table including all of the information
         * about them.
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
                url = new URL(displayItemsPHP);

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
                Log.w("ItemsActivity" , valueOf(response_code));
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

                    return ("unsuccessful" + displayItemsPHP + this.username);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

    }

    /**
     * This is a AsyncRetrieveFilter class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the filterChoice php file in the server which grabs and displays
     * all of the item that are under the specific filter the user selected in the app.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncRetrieveFilter extends AsyncTask<String, String, String> {
        String filterChoice;
        HttpURLConnection conn;
        URL url = null;
        private final String filterChoicePHP = "http://sarepach.cs.loyola.edu/UserConnection/filterChoice.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the filter choice that the user selected
         */
        public AsyncRetrieveFilter(String filterChoice){
            this.filterChoice = "?filterChoice=" + filterChoice;
        }

        /**
         * This will interact with UI and display loading message
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * This connects with the filterChoice php file on the server to display
         * all of the items in the specific category the user selected or ranked
         * in price depending on the order the user selected.
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
                url = new URL(filterChoicePHP + this.filterChoice);

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
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                Log.w("ItemsActivity" , valueOf(response_code));
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

                    return ("unsuccessful" + filterChoicePHP + this.filterChoice);
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