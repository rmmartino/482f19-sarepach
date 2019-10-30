package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ShippingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
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


}