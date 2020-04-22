package com.android.quizip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class RoundSelect extends AppCompatActivity {
    private static final String TAG = null;
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
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                if (TextUtils.isEmpty(rName.getText().toString().trim())){
                    rName.setError("Category Title Required");
                    return;
                }
                else {
                    categoryTitle = rName.getText().toString().trim();
                    // Create a new title
                    Map<String, Object> categoryTitle = new HashMap<>();
                    categoryTitle.put("title", getCategoryTitle());
                    db.collection("category_titles")
                            .add(categoryTitle)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }

                typeOfQuestion = qTypeSpinner.getSelectedItem().toString();

                if (typeOfQuestion.equalsIgnoreCase("matching")){
                    startActivity(new Intent(getApplicationContext(), MatchingActivity.class));
                }
                else if (typeOfQuestion.equalsIgnoreCase("multiple choice")){
                    startActivity(new Intent(getApplicationContext(), multipleChoiceActivity.class));
                }
                else if (typeOfQuestion.equalsIgnoreCase("true or false")){
                    startActivity(new Intent(getApplicationContext(), TFActivity.class));
                }
            }
        });
    }

    public static String getCategoryTitle(){
        return categoryTitle;
    }


}
