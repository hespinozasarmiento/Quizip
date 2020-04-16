package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RoundSelect extends AppCompatActivity {
    TextView nameView;
    EditText rName;
    Spinner qTypeSpinner;
    Button next;
    private static String categoryTitle;
    private String typeOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_select);

        nameView = findViewById(R.id.categoryName);
        rName = findViewById(R.id.roundName);
        qTypeSpinner = findViewById(R.id.questionTypeSelect);
        next = findViewById(R.id.nextButton2);

        //Increase the round Counter
        QuizCreationSettings.incRoundCounter();

        //Sets the text display to correctly be what the user inputted
        nameView.setText(QuizCreationSettings.getQuizName());
        rName.setHint("Round " + QuizCreationSettings.getRoundsCounter() + " Category Title" );


        //loads question input page based on the
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(rName.getText().toString().trim())){
                    rName.setError("Category Title Required");
                    return;
                }
                else {
                    categoryTitle = rName.getText().toString().trim();
                    //TODO save category title to database
                }

                typeOfQuestion = qTypeSpinner.getSelectedItem().toString();
                //TODO save category question type to database

                if (typeOfQuestion.equalsIgnoreCase("matching")){
                    startActivity(new Intent(getApplicationContext(), MatchingActivity.class));
                }
                else if (typeOfQuestion.equalsIgnoreCase("multiple choice")){
                    //TODO method for creating multiple choice input page
                }
                else if (typeOfQuestion.equalsIgnoreCase("true or false")){
                    //TODO method for creating tf input page
                }
            }
        });
    }

    public static String getCategoryTitle(){
        return categoryTitle;
    }


}
