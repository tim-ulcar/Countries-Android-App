package com.tim.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    String quizType;
    String selectedLength;
    boolean useAfrica;
    boolean useAsia;
    boolean useEurope;
    boolean useNorthAmerica;
    boolean useSouthAmerica;
    boolean useOceania;

    public void onCheckboxClicked(View checkBox) {
        boolean isChecked = ((CheckBox) checkBox).isChecked();
        int checkBoxId = checkBox.getId();
        if (R.id.europeCheckBox == checkBoxId) {
            if (isChecked) {
                useEurope = true;
            }
            else {
                useEurope = false;
            }
        }
        if (R.id.africaCheckBox == checkBoxId) {
            if (isChecked) {
                useAfrica = true;
            }
            else {
                useAfrica = false;
            }
        }
        if (R.id.asiaCheckBox == checkBoxId) {
            if (isChecked) {
                useAsia = true;
            }
            else {
                useAsia = false;
            }
        }
        if (R.id.southAmericaCheckBox == checkBoxId) {
            if (isChecked) {
                useSouthAmerica = true;
            }
            else {
                useSouthAmerica = false;
            }
        }
        if (R.id.northAmericaCheckBox == checkBoxId) {
            if (isChecked) {
                useNorthAmerica = true;
            }
            else {
                useNorthAmerica = false;
            }
        }
        if (R.id.oceaniaCheckBox == checkBoxId) {
            if (isChecked) {
                useOceania = true;
            }
            else {
                useOceania = false;
            }
        }
    }


    public void toQuiz(View view) {
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        intent.putExtra("quizType", quizType);
        intent.putExtra("selectedLength", selectedLength);
        intent.putExtra("useEurope", useEurope);
        intent.putExtra("useAfrica", useAfrica);
        intent.putExtra("useAsia", useAsia);
        intent.putExtra("useSouthAmerica", useSouthAmerica);
        intent.putExtra("useNorthAmerica", useNorthAmerica);
        intent.putExtra("useOceania", useOceania);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent intent = getIntent();
        quizType = intent.getStringExtra("buttonName");
        quizType = quizType.replace("Button", "");

        //Set up spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> legthOptions = new ArrayList<String>();
        legthOptions.add("5");
        legthOptions.add("10");
        legthOptions.add("20");
        legthOptions.add("30");
        legthOptions.add("40");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, legthOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLength = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        useEurope = true;
        useAfrica = false;
        useAsia = false;
        useNorthAmerica = false;
        useSouthAmerica = false;
        useOceania = false;


    }
}
