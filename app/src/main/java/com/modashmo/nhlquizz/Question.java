package com.modashmo.nhlquizz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mauricio on April 28, 2017
 * <p>
 * Udacity's Android Basics Nanodegree
 * Project 3: Quiz App
 */

public class Question {

    String question;            //  Question to be shown as a TextView to the user
    List<Answer> answers;       //  List of instances of Answer that will hold text and boolean values
    byte questionType;          //  1 = Multiple right answers; 2 = single right answer; 3 = Text entry answer

    //  Empty constructor
    public Question() {

    }

    /**
     * @param question:     Question title to be displayed as a {@link android.widget.TextView} to the user
     * @param questionType: Byte value that will determine if the question has Multiple, Single or Text answers
     */
    public Question(String question, byte questionType) {
        this.question = question;
        this.answers = new ArrayList<>();
        this.questionType = questionType;
    }

    /**
     * @param answer:  Instance of the class {@link Answer} used as one possible answer to the {@link Question}.
     *                 It will be displayed as either {@link android.widget.RadioButton}, {@link android.widget.CheckBox} or
     *                 checked against a {@link String} value.
     * @param isRight: Determines whether or not this specific {@link Answer} is correct or incorrect when checked against
     *                 a {@link android.widget.RadioButton} or {@link android.widget.CheckBox}.
     */
    public void addAnswer(String answer, boolean isRight) {
        this.answers.add(new Answer(answer, isRight));
    }

    /**
     * Method to return a {@link String} value
     */
    public String getQuestion() {
        return question;
    }

}
