
package com.android.quizip;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class QuizProcessor {
    private ArrayList<TrueFalse> trueFalse = new ArrayList<>();
    private ArrayList<Matching> matching = new ArrayList<>();
    private ArrayList<MultipleChoice> multipleChoice = new ArrayList<>();
    private QParser parser;
    private ArrayList<Question> mixed = new ArrayList<>();
    private int counter = 0;


    public QuizProcessor(String fileName) {

        parser = new QParser(fileName);
        parser.parseTrueFalse();
        parser.parseMatching();
        parser.parseMultipleChoice();

        if (!trueFalse.isEmpty()) {
            mixed.addAll(trueFalse);
        }
        if (!matching.isEmpty()) {
            mixed.addAll(matching);
        }
        if (!multipleChoice.isEmpty()) {
            mixed.addAll(multipleChoice);
        }
        Collections.shuffle(mixed);
    }

    public Question getQuestion() {
        if (!mixed.isEmpty()) {
            Question temp = mixed.get(0);
            mixed.remove(0);
            return temp;
        } else
            //TODO method for ending a quiz
            return null;
    }

    public void resetCounter() {
        counter = 0;
    }

    public QParser getQParser(){
        return parser;
    }

    public void setTrueFalse(ArrayList<TrueFalse> trueFalse){
        this.trueFalse = trueFalse;
    }
    public void setMatching(ArrayList<Matching> matching){
        this.matching = matching;
    }

    public void setMultipleChoice(ArrayList<MultipleChoice> multipleChoice){
        this.multipleChoice = multipleChoice;
    }

    public boolean checkAnswer(String userAnswer, Question question) {
        QTypes type = question.getType();

        if (QTypes.TRUE_FALSE.equals(type)) {
            return userAnswer.equalsIgnoreCase((String) question.getAnswers().get(0));
        }

        else if (QTypes.MATCHING.equals(type)) {

            if (question.getAnswers().get(counter).equals(userAnswer)) {
                counter++;
                return true;
            } else {
                counter++;
                return false;
            }

        } else if (QTypes.MULTIPLE_CHOICE.equals(type)) {
            MultipleChoice q = (MultipleChoice) question;
            for (Map.Entry<String, String> entry : q.getAnswers()) {
                if (userAnswer.equalsIgnoreCase(entry.getKey()) && entry.getValue().equals("C")) {
                    return true;
                }
            }
            return false;
        } else {
            System.out.println("something went wrong - returning false");
            return false;
        }
    }

    public Boolean mixedHasNext() {
        if (!mixed.isEmpty()) {
            return true;
        } else return false;
    }


}

