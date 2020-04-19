package com.android.quizip;

import java.util.ArrayList;
import java.util.Arrays;

public class TrueFalse implements Question {
    private String question;
    private String answer;

    public TrueFalse(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public QTypes getType() {
        return QTypes.TRUE_FALSE;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return new ArrayList<String>(Arrays.asList(answer));
    }
}
