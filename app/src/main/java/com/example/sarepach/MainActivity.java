package com.example.sarepach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onStart() {
        super.onStart();
    }


    public void goProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    public void gosignup(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }
}