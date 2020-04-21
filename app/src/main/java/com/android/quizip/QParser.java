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

    public ArrayList<TrueFalse> parseTrueFalse() {
        final ArrayList trueFalse = new ArrayList<TrueFalse>();
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()){
                Map<String, String> tempMap = (Map) documentSnapshot.get("trueFalse");
                for (Map.Entry<String, String> entry : tempMap.entrySet()){
                    trueFalse.add(new TrueFalse(entry.getKey(), entry.getValue()));
                }
            }
            else {
            }
            }
        });
       return trueFalse;
    }

    public ArrayList<Matching> parseMatching() {
        final ArrayList<Matching> matching = new ArrayList<>();
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()){
                   Map<String, Map<String, String>> tempMap = (Map) documentSnapshot.get("matching");
                   for (Map.Entry<String, Map<String, String>> set : tempMap.entrySet()) {
                       matching.add(new Matching(set.getKey(), new ArrayList<>(set.getValue().entrySet())));
                   }

               }
           }
       });
        return matching;
    }

    public ArrayList<MultipleChoice> parseMultipleChoice() {
        final ArrayList<MultipleChoice> multipleChoice = new ArrayList<>();
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                   Map<String, Map<String, String>> tempMap = (Map) documentSnapshot.get("multipleChoice");
                    for (Map.Entry<String, Map<String, String>> set : tempMap.entrySet()) {
                        multipleChoice.add(
                                new MultipleChoice(set.getKey(), new ArrayList<>(set.getValue().entrySet())));
                    }

                }
            }
        });

        return multipleChoice;
    }
}
