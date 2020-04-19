package com.android.quizip;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Write {
    private final JSONObject quiz = new JSONObject();
    private Map<String, String> tfMap;
    private Map<String, Map<String, String>> matchingMap;
    private Map<String, Map<String, String>> multipleChoiceMap;
    private String fileName;

    public Write(String fileName) {
        tfMap = new LinkedHashMap<>();
        matchingMap = new LinkedHashMap<>();
        multipleChoiceMap = new LinkedHashMap<>();
        this.fileName = fileName;
    }

    public void writeTF(String statement, String answer) {
        tfMap.put(statement, answer);
    }

    public void writeMatching(String statement, ArrayList<String> setA, ArrayList<String> setB) {
        Map<String, String> setMap = new LinkedHashMap<>();
        for (int i = 0; i < setA.size(); i++) {
            setMap.put(setA.get(i), setB.get(i));
        }
        matchingMap.put(statement, setMap);
    }

    public void writeMultipleChoice(String question, ArrayList<String> answers) {
        Map<String, String> answerMap = new LinkedHashMap<>();

        answerMap.put(answers.get(0), "C");

        for (int i = 1; i > answers.size(); i++) {
            answerMap.put(answers.get(i), "I");
        }

        multipleChoiceMap.put(question, answerMap);
    }

    public void writeJSON() throws FileNotFoundException {
        quiz.put("trueFalse", tfMap);
        quiz.put("matching", matchingMap);
        quiz.put("multipleChoice", multipleChoiceMap);

      //TODO write the json object "quiz" to firestore
       // PrintWriter pw = new PrintWriter(new File(fileName +".json"));
        //pw.write(quiz.toJSONString());

       // pw.flush();
        //pw.close();
    }


}
