package com.modashmo.nhlquizz;

/**
 * Created by Mauricio on April 28, 2017
 * <p>
 * Udacity's Android Basics Nanodegree
 * Project 3: Quiz App
 */

public class Answer {

    private String answer;
    private boolean isCorrect;

    //  Empty constructor
    public Answer() {

    }

    /**
     * @param answer:    Answer option to be displayed as a text in a {@link android.widget.CheckBox} or {@link android.widget.RadioButton}
     * @param isCorrect: Determines whether or not this specific {@link Answer} is correct or incorrect when checked against
     *                   a {@link android.widget.RadioButton} or {@link android.widget.CheckBox}.
     */
    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public String getAnswer() {
        return this.answer;
    }

    /**
     * Method toString() is override so in order to show answer text
     * instead of the object ID when necessary
     */
    @Override
    public String toString() {
        return answer;
    }
}
