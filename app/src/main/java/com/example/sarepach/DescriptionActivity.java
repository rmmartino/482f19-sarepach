package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

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
}