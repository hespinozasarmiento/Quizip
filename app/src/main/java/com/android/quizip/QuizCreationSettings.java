package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizCreationSettings extends AppCompatActivity {
    Button back, next;
    EditText rInput, qInput, qName;
    private static String quizName = new String();
    private static int roundsAmount = 0;
    private static int roundCounter = 0;
    private static int questionsPerRound = 0;
    private static int questionCounter = 0;
    private static String roundType = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creation_settings);

        rInput = findViewById(R.id.numberOfRoundsInput);
        qInput = findViewById(R.id.numberOfQuestionsInput);
        qName = findViewById(R.id.quizNameInput);
        back = findViewById(R.id.backButton2);
        next = findViewById(R.id.nextButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                roundsAmount = Integer.parseInt(rInput.getText().toString().trim());
                quizName = qName.getText().toString().trim();
                questionsPerRound = Integer.parseInt(qInput.getText().toString().trim());

                if (roundsAmount <= 0){
                    rInput.setError("Must enter a value > 0");
                    return;
                }

                if (questionsPerRound <= 0){
                    qInput.setError("Must enter a value > 0");
                    return;
                }

                if (TextUtils.isEmpty(quizName)){
                    qName.setError("Name Required.");
                    return;
                }




                startActivity(new Intent(getApplicationContext(), RoundSelect.class));


            }
        });


    }

    public static String getQuizName(){
        return quizName;

    }
}

