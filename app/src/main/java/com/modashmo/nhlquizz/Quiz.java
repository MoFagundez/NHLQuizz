package com.modashmo.nhlquizz;

/**
 *  Created by Mauricio on April 26, 2017
 *  based on tutorials and examples found in the web
 *
 *  Udacity's Android Basics Nanodegree
 *  Project 2: Quiz App
 */

public class Quiz {

    /*
     *  Class variables, please see description in the @params documentation below
     */
    private int questionId;
    private String question;
    private String answerCorrect;
    private String answerWrong1;
    private String answerWrong2;
    private String answerWrong3;


    public Quiz(){
        // Empty constructor
    }

    /**
     *  Constructor for building a complete question (Quiz object)
     *
     *  @param questionId:      ID to be used in the future with SQLite.
     *  @param question:        Question title to be displayed in a TextView.
     *  @param answerCorrect:   Correct answer to the question.
     *  @param answerWrong1:    First of three wrong answers to be randomly placed in a ListView.
     *  @param answerWrong2:    Second of three wrong answers to be randomly placed in a ListView.
     *  @param answerWrong3:    Last of three wrong answers to be randomly placed in a ListView.
     */
    public Quiz(int questionId, String question, String answerCorrect, String answerWrong1, String answerWrong2, String answerWrong3){
        this.questionId = questionId;
        this.question = question;
        this.answerCorrect = answerCorrect;
        this.answerWrong1 = answerWrong1;
        this.answerWrong2 = answerWrong2;
        this.answerWrong3 = answerWrong3;
    }

    /**
     *  Class Getters to be used according to @params documentation and description
     */
    public int getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public String getAnswerWrong1() {
        return answerWrong1;
    }

    public String getAnswerWrong2() {
        return answerWrong2;
    }

    public String getAnswerWrong3() {
        return answerWrong3;
    }

    /**
     *  Class Setters to be used according to @params documentation and description
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public void setAnswerWrong1(String answerWrong1) {
        this.answerWrong1 = answerWrong1;
    }

    public void setAnswerWrong2(String answerWrong2) {
        this.answerWrong2 = answerWrong2;
    }

    public void setAnswerWrong3(String answerWrong3) {
        this.answerWrong3 = answerWrong3;
    }

}
