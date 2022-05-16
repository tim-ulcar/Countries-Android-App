package com.tim.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinishedActivity extends AppCompatActivity {


    public void returnToMainScreen(View button) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);

        Intent intent = getIntent();
        int finalScore = intent.getIntExtra("finalScore", 0);
        String selectedLength = intent.getStringExtra("selectedLength");

        TextView finalScoreTextView = (TextView) findViewById(R.id.finalScoreTextView);
        String forFinalScoreTextView = "FINAL SCORE: " + finalScore + " / " + selectedLength;
        finalScoreTextView.setText(forFinalScoreTextView);

        TextView ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        String forRatingTextView;
        double percentage = finalScore/Double.parseDouble(selectedLength);
        if (percentage >= 0.9) {
            forRatingTextView = "A";
        }
        else if (percentage < 0.9 && percentage >= 0.8) {
            forRatingTextView = "B";
        }
        else if (percentage < 0.8 && percentage >= 0.7) {
            forRatingTextView = "C";
        }
        else if (percentage < 0.7 && percentage >= 0.6) {
            forRatingTextView = "D";
        }
        else if (percentage < 0.6 && percentage >= 0.5) {
            forRatingTextView = "E";
        }
        else {
            forRatingTextView = "F";
        }
        ratingTextView.setText(forRatingTextView);

    }
}
