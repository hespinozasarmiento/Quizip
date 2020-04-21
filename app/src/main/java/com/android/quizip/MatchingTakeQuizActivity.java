package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MatchingTakeQuizActivity extends AppCompatActivity {
Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_take_quiz);

        next = findViewById(R.id.matchingNextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizHost.getNextQuestion(MatchingTakeQuizActivity.this);
                finish();
            }
        });
    }
}
