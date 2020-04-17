package com.android.quizip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class multipleChoiceActivity extends AppCompatActivity {

    TextView nameView, qView;
    EditText qStatement, setA;
    Button next, addA;
    ListView setAList;
    ArrayAdapter<String> adapterA;
    ArrayList<String> listItemsA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);


        //increases the question counter
        QuizCreationSettings.incQuestionCounter();

        qView = findViewById(R.id.questionNumber2);
        listItemsA = new ArrayList<String>();
        nameView = findViewById(R.id.categoryName2);
        qStatement = findViewById(R.id.questionStatementField2);
        setA = findViewById(R.id.matchingSet);
        next = findViewById(R.id.nextButton4);
        addA = findViewById(R.id.addA2);
        setAList = findViewById(R.id.matchingSetAList2);

        adapterA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemsA);
        setAList.setAdapter(adapterA);

        nameView.setText(RoundSelect.getCategoryTitle());
        qView.setText("Question " + QuizCreationSettings.getQuestionCounter());

        addA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(setA.getText().toString().trim())){
                    setA.setError("Must enter text.");
                    return;
                }
                adapterA.add(setA.getText().toString().trim());
                setA.setText(null);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(qStatement.getText().toString().trim())){
                    qStatement.setError("Must enter a Question");
                    return;
                }

                //TODO store the question statement in the database along with the  multiple choice answer array set


                if (QuizCreationSettings.nextQuestion() ==0){
                    startActivity(new Intent(getApplicationContext(), multipleChoiceActivity.class));
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }
        });

    }
}
