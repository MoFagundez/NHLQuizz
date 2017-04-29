package com.modashmo.nhlquizz;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Mauricio on April 28, 2017
 *  based on teachings from a more
 *  experienced developer/friend.
 *
 *  Udacity's Android Basics Nanodegree
 *  Project 2: Quiz App
 */
public class Question {

    String question;
    List<Answer> answers;

    public Question() {

    }

    /**
     *  Documentation pending...
     */
    public Question(String question) {
        this.question = question;
        this.answers = new ArrayList<>();
    }

    /**
     *  Documentation pending...
     */
    public void addAnswer(String answer, boolean isRight) {
        this.answers.add(new Answer(answer, isRight));
    }

    /**
     *  Documentation pending...
     */
    public String getQuestion() {
        return question;
    }

}
