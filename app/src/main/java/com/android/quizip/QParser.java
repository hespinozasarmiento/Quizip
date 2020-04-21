package com.android.quizip;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class QParser {
    private String fileName;
    private DocumentReference documentReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    QParser(String fileName) {
    this.fileName = fileName;
    fStore = FirebaseFirestore.getInstance();
    fAuth = FirebaseAuth.getInstance();
    documentReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid()).collection("quizzes").document(fileName);
    }

    public void parseTrueFalse() {
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, String> tempMap;
                ArrayList<TrueFalse> trueFalse = new ArrayList<>();
                if (task.isSuccessful()){
                    tempMap = (Map<String, String>) task.getResult().get("trueFalse");
                    for (Map.Entry<String, String> entry : tempMap.entrySet()) {
                        trueFalse.add(new TrueFalse(entry.getKey(), entry.getValue()));
                    }
                    QuizHost.getQuizProcessor().setTrueFalse(trueFalse);
                }
            }
        });
    }


//TODO figure out how to read the sub collection in the document. Currently only returns the top level query
    public void parseMatching() {
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
           public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                Map<String, Map<String, String>> tempMap;
                ArrayList<Matching> matching = new ArrayList<>();
               if (task.isSuccessful()){
                    tempMap = (Map<String, Map<String, String>>) task.getResult().get("matching");
                   for (Map.Entry<String, Map<String, String>> set : tempMap.entrySet()) {
                       matching.add(new Matching(set.getKey(), set.getValue()));
                   }
                   QuizHost.getQuizProcessor().setMatching(matching);
               }
           }
       });
    }

    //TODO figure out how to read the sub collection in the document. Currently only returns the top level query
    public void parseMultipleChoice() {
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Map<String, String>> tempMap;
                ArrayList<MultipleChoice> multipleChoice = new ArrayList<>();
                if (task.isSuccessful()){
                    tempMap = (Map<String, Map<String, String>>) task.getResult().get("multipleChoice");
                    for (Map.Entry<String, Map<String, String>> set : tempMap.entrySet()) {
                        multipleChoice.add(new MultipleChoice(set.getKey(), set.getValue()));
                    }
                    QuizHost.getQuizProcessor().setMultipleChoice(multipleChoice);
                }
            }
        });
}
}
