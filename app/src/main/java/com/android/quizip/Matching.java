package com.android.quizip;

import java.util.ArrayList;
import java.util.Map;

public class Matching implements Question {
    private String statement;
    private ArrayList<String> matchingKey;
    private ArrayList<String> answers;
    private ArrayList<String> tempArray, tempArrayTwo;

    public Matching(String statement, ArrayList<Map.Entry<String, String>> questions) {
        this.statement = statement;
        matchingKey = new ArrayList<>();
        answers = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            matchingKey.add(questions.get(i).getKey());
            answers.add(questions.get(i).getValue());
        }
        tempArray = matchingKey;
        tempArrayTwo = matchingKey;
    }

    @Override
    public QTypes getType() {
        return QTypes.MATCHING;
    }

    @Override
    public String getQuestion() {
        return statement;
    }

    public String getNextStatement() {
        String temp = null;
        if (!tempArrayTwo.isEmpty()) {
            temp = tempArrayTwo.get(0);
            tempArrayTwo.remove(0);
        }
        return temp;
    }

    public String getNextMatchingKey() {
        String temp = null;
        if (!tempArray.isEmpty()) {
            temp = tempArray.get(0);
            tempArray.remove(0);
        }
        return temp;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getMatchingKey() {
        return matchingKey;
    }

    public int getMatchingKeySize() {
        return matchingKey.size();
    }
}