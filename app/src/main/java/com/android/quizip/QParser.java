package com.android.quizip;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class QParser {
    private JSONObject quiz;

    QParser(JSONObject quiz) {

        this.quiz = quiz;
    }
    public ArrayList<TrueFalse> parseTrueFalse() {
        Map<String, String> tfMap = (Map) quiz.get("trueFalse");
        ArrayList trueFalse = new ArrayList<TrueFalse>();

        for (Map.Entry<String, String> entry : tfMap.entrySet()) {
            trueFalse.add(new TrueFalse(entry.getKey(), entry.getValue()));
        }
        return trueFalse;
    }

    public ArrayList<Matching> parseMatching() {
        Map<String, Map<String, String>> m = (Map) quiz.get("matching");
        ArrayList<Matching> matching = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> set : m.entrySet()) {
            matching.add(new Matching(set.getKey(), new ArrayList<>(set.getValue().entrySet())));
        }
        return matching;
    }

    public ArrayList<MultipleChoice> parseMultipleChoice() {
        Map<String, Map<String, String>> m = (Map) quiz.get("multipleChoice");
        ArrayList<MultipleChoice> multipleChoice = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> set : m.entrySet()) {
            multipleChoice.add(
                    new MultipleChoice(set.getKey(), new ArrayList<>(set.getValue().entrySet())));
        }
        return multipleChoice;
    }
}
