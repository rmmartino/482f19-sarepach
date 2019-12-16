package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

}