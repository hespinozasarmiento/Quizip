package com.android.quizip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MultipleChoice implements Question {
    private String question;
    private ArrayList<Map.Entry<String, String>> answers;

    public MultipleChoice(String question, ArrayList<Map.Entry<String, String>> answers) {
        this.question = question;
        this.answers = answers;
    }

    @Override
    public QTypes getType() {
        return QTypes.MULTIPLE_CHOICE;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswerStrings() {
        ArrayList<String> answerStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : answers) {
            answerStrings.add(entry.getKey());
        }

        Collections.shuffle(answerStrings);
        return answerStrings;
    }

    @Override
    public ArrayList<Map.Entry<String, String>> getAnswers() {
        return answers;
    }
}
