package com.android.quizip;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class QuizHost {
    private static Question currentQuestion;

    public static void getNextQuestion(Context context) {

        if (QuizProcessor.mixedHasNext()) {
            currentQuestion = QuizProcessor.getQuestion();
            if (currentQuestion.getType() == QTypes.TRUE_FALSE) {
                context.startActivity(new Intent(context.getApplicationContext(), TFTakeQuizActivity.class));

            } else if (currentQuestion.getType() == QTypes.MATCHING) {
                context.startActivity(new Intent(context.getApplicationContext(), MatchingTakeQuizActivity.class));

            } else if (currentQuestion.getType() == QTypes.MULTIPLE_CHOICE) {
                context.startActivity(new Intent(context.getApplicationContext(), MultipleChoiceTakeQuizActivity.class));
            }
            //TODO method for ending the quiz
        } else ;
    }

    public static Question getCurrentQuestion(){
        return currentQuestion;
    }
}
