package com.example.sarepach;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.valueOf;

/**
 * This is a ProfileActivity class that corresponds with the screen of the app that
 * displays the profile page of the user
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app the shows the profile page of the user
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String result = "";

        try {
            AsyncRetrieveProfileBids asyncTask = new AsyncRetrieveProfileBids();
            result = asyncTask.execute().get();
            //AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
            //alertDialog.setTitle("Display Result");
            //alertDialog.setMessage(result);
            //alertDialog.show();
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            //tableLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addItemEntitys(tableLayout, result);
        }
        catch(Exception e){
            Log.w("ProfileActivityCreate", e);
        }

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
     * Sets up the screen that goes to the description of an item page
     *
     * @param v
     *            the screen view
     */
    public void goDescription(View v, String item) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        //intent.putExtra()
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that goes to the settings page
     *
     * @param v
     *            the screen view
     */
    public void gosettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that displays the message that tells the user who to email
     * when they want to donate an item for auction
     *
     * @param v
     *            the screen view
     */
    public void sendEmail(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setMessage("Please send an email to auctionBSO@gmail.com" );
        alertDialog.show();
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

            // Add  view to the new row
            addImageView(tableRow, item);
            addTextView(tableRow, item);
            addButton(tableRow, item);


        }catch (Exception e) {
            Log.w("ProfileActivityTableRow", e);
        }
        //addButton();
        //ImageView imageView = new ImageView(getApplicationContext());
        //TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        //imageView.setLayoutParams(layoutParams);
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

        imageView.setImageResource(R.drawable.bsoshort);
        //imageView.setMaxHeight(2);
        //imageView.getLayoutParams().width=150;

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        v.addView(imageView, 200 ,200);

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
        //textView.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        textView.setTextSize(24f);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.parseColor("#DDDDDD"));

        textView.setText(item.split(";")[0]);
        textView.setHeight(200);
        textView.setWidth(700);
        v.addView(textView);

    }

    public void addButton(TableRow v, String item){
        Button button = new Button(this);
        //button.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        //button.setLayoutParams(layoutParams);
        button.setTextSize(16f);
        button.setTextColor(Color.BLACK);
        button.setBackgroundColor(Color.parseColor("#DDDDDD"));
        button.setHeight(200);
        button.setWidth(250);
        button.setOnClickListener(
                new View.OnClickListener( )
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
                        // Have to get item id from the item clicked on then pass it to description activity
                        //goDescription(null, item );

                    }
                });

        String isTopBidder = (item.split(";")[3]).trim();
        if (isTopBidder.equals("true")){
            button.setText("You\'re Winning!");
        }
        else{
            button.setText("You\'ve been outbid!");
        }

        v.addView(button);
    }

    protected class MyLovelyOnClickListener implements View.OnClickListener
    {

        String itemName;
        public MyLovelyOnClickListener(String itemName) {
            //this.myLovelyVariable = myLovelyVariable;
        }

        @Override
        public void onClick(View v)
        {
            //read your lovely variable
        }

    };

    /**
     * This is a AsyncRetrieveProfileBids class that uses the Android Studio library,
     * AsyncTask which allows for the communication between the server and the app.
     * This connects with the validateUser php file in the server which checks if the username
     * and password combination of the user are correct.
     *
     * @author SaRePaCh
     * @version 1.0 12/15/2019
     */
    protected class AsyncRetrieveProfileBids extends AsyncTask<String, String, String> {
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
        public AsyncRetrieveProfileBids(){
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

