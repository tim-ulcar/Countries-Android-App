package com.tim.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void toSecondActivity(View button) {
        int buttonId = button.getId();
        String buttonName = button.getResources().getResourceEntryName(buttonId);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("buttonName", buttonName);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
