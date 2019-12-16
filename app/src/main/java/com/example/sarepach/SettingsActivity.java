package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * This is a SettingsActivity class that corresponds with the screen of the app that
 * shows up when the user clicks on their settings in their profile
 *
 * @author SaRePaCh
 * @version 1.0 12/15/2019
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Creates the screen of the app that displays the users settings
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Opens the app on the start
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Sets up the screen that displays the users profile page
     *
     * @param v
     *            the screen view
     */
    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that allows the user to change their password which is
     * under their settings
     *
     * @param v
     *            the screen view
     */
    public void goChange(View v) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        this.startActivity(intent);
    }

}