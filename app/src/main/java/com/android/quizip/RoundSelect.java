package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RoundSelect extends AppCompatActivity {
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_select);

        nameView = findViewById(R.id.quizName);
        nameView.setText(QuizCreationSettings.getQuizName());
    }
}
