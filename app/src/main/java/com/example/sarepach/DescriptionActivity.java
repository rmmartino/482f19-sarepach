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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.valueOf;

/**
 * This is a DescriptionActivity class that corresponds with the screen of the description
 * of a single item
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class DescriptionActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app with the description of a single item
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button bidButton;
        final int itemId;
        final String itemName;
        final EditText amount;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        bidButton = (Button) findViewById(R.id.bidID);
        itemName = getIntent().getStringExtra("ITEM_ID");

        // Execute PHP to get all item information
        AsyncNewBid asyncNewBid = new AsyncNewBid(itemName);
        try {
            String itemInfo = asyncNewBid.execute().get();
            displayItemInfo(itemInfo);
        }catch(Exception e){
            Log.w("DescriptionActivity", "Could not retreive item info: " + e);
        }

        // Want to wait for user to click on Bid
        bidButton.setOnClickListener(
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
                        try {
                            //TODO: Create new constructor for async send bid data
                            AsyncNewBid asyncTask = new AsyncNewBid(itemName);
                            String result = asyncTask.execute().get();
                            if(result.equals("Failure")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(DescriptionActivity.this).create();
                                alertDialog.setTitle("Not inserted correctly");
                                alertDialog.show();
                            }
                        } catch(Exception e) {
                            Log.w("DescriptionActivity", e);
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
     * Sets up the screen that has the payment input on it after a user chooses
     * to bid on an item
     *
     * @param v
     *            the screen view
     */
    public void gopaymentpage(View v) {
        Intent intent = new Intent(this, PaymentActivity.class);
        this.startActivity(intent);
    }

    public void displayItemInfo(String itemInfo) {
        // Parse all item info for just the item name to display first
        String itemName = itemInfo.split(";")[0];
        TextView textView = (TextView) findViewById(R.id.itemID);
        textView.setText(itemName);

        ImageView imageView = (ImageView) findViewById(R.id.imageID);
        Picasso.get().load(itemInfo.split(";")[1]).into(imageView);

        textView = (TextView) findViewById(R.id.itemdescriptionID);
        textView.setText(itemInfo.split(";")[2]);

        String bidInfo = "Current Bid: $" + itemInfo.split(";")[3] + "\n Your Min Bid: $" + itemInfo.split(";")[4].replace("_", "");
        textView = (TextView) findViewById(R.id.bidInfoID);
        textView.setText(bidInfo);



    }

    /**
     * This is a AsyncNewBid class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This retrieves a new bid where a user has placed a bid on an item and inserts
     * it into the database table.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncNewBid extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        String email;
        String itemName;
        HttpURLConnection conn;
        URL url = null;
        private final String displaySingleItem = "http://sarepach.cs.loyola.edu/UserConnection/displaySingleItem.php";
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        /**
         * Retrieves the email of the user, id of the item they're bidding on, and the amount
         * they're bidding on the item
         *
         * @param itemName
         *            the name of the item
         */
        public AsyncNewBid(String itemName){
            this.itemName = "?name=" + itemName;
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
                url = new URL(displaySingleItem  + this.itemName);

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