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
    Button next;
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

        next = findViewById(R.id.nextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quizName = qName.getText().toString().trim();


                if (TextUtils.isEmpty(rInput.getText().toString().trim())){
                    rInput.setError("Must enter a value > 0");
                    return;
                }
                else {
                    roundsAmount = Integer.parseInt(String.valueOf(rInput.getText()));
                }

                if (TextUtils.isEmpty(qInput.getText().toString().trim())){
                    qInput.setError("Must enter a value > 0");
                    return;
                }
                else {
                    questionsPerRound = Integer.parseInt(String.valueOf(qInput.getText()));
                }

                if (TextUtils.isEmpty(quizName)){
                    qName.setError("Name Required.");
                    return;
                }

                else {
                    //TODO save quiz name in database as the file name
                }

                startActivity(new Intent(getApplicationContext(), RoundSelect.class));


            }
        });


    }

    public static String getQuizName(){
        return quizName;
    }

    public static int getRoundsCounter(){
        return roundCounter;
    }

    public static int getQuestionCounter(){
        return questionCounter;
    }

    public static void incRoundCounter(){
        roundCounter++;
    }

    public static void incQuestionCounter(){
        questionCounter++;
    }

    public static int nextQuestion() {
        if (questionCounter < questionsPerRound) {
            return 0;
        }
        if (roundCounter < roundsAmount) {
            //TODO method for next round
            return 1;
        }
            return 2;
        }
    }



