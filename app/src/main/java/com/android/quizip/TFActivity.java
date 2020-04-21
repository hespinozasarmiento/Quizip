package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class TFActivity extends AppCompatActivity {

    Spinner tfSpinner;
    TextView nameView, qView;
    EditText qStatement;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_f);

        //increases the question counter
        QuizCreationSettings.incQuestionCounter();

        qView = findViewById(R.id.questionNumber3);
        nameView = findViewById(R.id.categoryName3);
        tfSpinner = findViewById(R.id.TFSelect);
        qStatement = findViewById(R.id.questionStatementField3);
        next = findViewById(R.id.nextButton5);

        nameView.setText(RoundSelect.getCategoryTitle());
        qView.setText("Question " + QuizCreationSettings.getQuestionCounter());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(qStatement.getText().toString().trim())){
                    qStatement.setError("Must enter a Question");
                    return;
                }

                //writes the question to the json file
                QuizCreationSettings.getWriter().writeTF(qStatement.getText().toString().trim(), tfSpinner.getSelectedItem().toString());


                if (QuizCreationSettings.nextQuestion() ==0){
                    startActivity(new Intent(getApplicationContext(), TFActivity.class));
                    return;
                }
                if (QuizCreationSettings.nextQuestion() ==1){
                    QuizCreationSettings.resetQuestionCounter();
                    startActivity(new Intent(getApplicationContext(), RoundSelect.class));
                    return;
                }
                if (QuizCreationSettings.nextQuestion() ==2){
                    try {
                        QuizCreationSettings.getWriter().writeJSON();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //TODO quiz creation completion notification
                    Context context = getApplicationContext();
                    CharSequence text = "Quiz Created!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });
    }
}
