package com.android.quizip;

import java.util.ArrayList;

public interface Question {
    QTypes getType();

    String getQuestion();

    ArrayList getAnswers();
}
