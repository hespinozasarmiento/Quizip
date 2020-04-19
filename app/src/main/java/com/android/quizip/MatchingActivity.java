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

public class MatchingActivity extends AppCompatActivity {

    TextView nameView, qView;
    EditText qStatement, setA, setB;
    Button next, addA, addB;
    ListView setAList, setBList;
    ArrayAdapter<String> adapterA, adapterB;
    ArrayList<String> listItemsA, listItemsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        //increases the question counter
        QuizCreationSettings.incQuestionCounter();

        qView = findViewById(R.id.questionNumber);
        listItemsA = new ArrayList<String>();
        listItemsB = new ArrayList<String>();
        nameView = findViewById(R.id.categoryName);
        qStatement = findViewById(R.id.questionStatementField);
        setA = findViewById(R.id.matchingSet1);
        setB = findViewById(R.id.matchingSet2);
        next = findViewById(R.id.nextButton3);
        addA = findViewById(R.id.addA);
        addB = findViewById(R.id.addB);
        setAList = findViewById(R.id.matchingSetAList);
        setBList = findViewById(R.id.matchingSetBList);

        adapterA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemsA);
        adapterB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItemsB);
        setAList.setAdapter(adapterA);
        setBList.setAdapter(adapterB);

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

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(setB.getText().toString().trim())){
                    setB.setError("Must enter text.");
                    return;
                }
                adapterB.add(setB.getText().toString().trim());
                setB.setText(null);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(qStatement.getText().toString().trim())){
                    qStatement.setError("Must enter a Question");
                    return;
                }

                if (listItemsA.isEmpty() || listItemsB.isEmpty()){
                    setA.setError("Must enter at least 1 matching set");
                    setB.setError("Must enter at least 1 matching set");
                    return;
                }

                if (listItemsA.size() != listItemsB.size()){
                    next.setError("Set A and B must have the same amount of entries.");
                    return;
                }

                //writes matching questions to the json file.
                QuizCreationSettings.getWriter().writeMatching(qStatement.getText().toString().trim(), listItemsA, listItemsB);


                if (QuizCreationSettings.nextQuestion() ==0){
                    startActivity(new Intent(getApplicationContext(), MatchingActivity.class));
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });
    }
}
