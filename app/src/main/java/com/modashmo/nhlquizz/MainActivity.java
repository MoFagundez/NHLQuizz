package com.modashmo.nhlquizz;

/**
 *  NHL Quizz
 *  Created by Mauricio on April 26, 2017
 *  based on tutorials and examples found in the web
 *
 *  Udacity's Android Basics Nanodegree
 *  Project 2: Quiz App
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Quiz[] quizList;
    ListView answersListView;
    String userAnswer;
    String correctAnswer;
    int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("NHL Quizz")
                .setMessage("Game is about to start! You have 30 seconds to yadda yadda bla bla bla ooga booga oggy ogelthorpe! Are you ready?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //  Initialise quiz database: see method documentation
                        addQuizDatabase();

                        //  Generate a new question and populate screen when app is initialised
                        generateNewQuestion();

                        //  Start countdowm timer
                        startCountDown();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     *  The addQuizDatabase method is a void type method that create Quiz objects
     *  to be populated on the screen. Ideally this method should pull data from
     *  an SQLite database or RESTful API.
     *
     *  For this project, I am manually creating the questions since it does not
     *  require any of the storage methods above mentioned.
    * */
    public void addQuizDatabase() {
        //  The code below creates as many Quiz objects as we need for the app
        Quiz quiz0 = new Quiz(0, "Who let the dogs out?", "Yes1", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz1 = new Quiz(1, "Who let the dogs out?", "Yes2", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz2 = new Quiz(2, "Who let the dogs out?", "Yes3", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz3 = new Quiz(3, "Who let the dogs out?", "Yes4", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz4 = new Quiz(4, "Who let the dogs out?", "Yes5", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz5 = new Quiz(5, "Who let the dogs out?", "Yes6", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz6 = new Quiz(6, "Who let the dogs out?", "Yes7", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz7 = new Quiz(7, "Who let the dogs out?", "Yes8", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz8 = new Quiz(8, "Who let the dogs out?", "Yes9", "NOT ME!", "WOOF WOOF!", "WOOF!");
        Quiz quiz9 = new Quiz(9, "Who let the dogs out?", "Yes10", "NOT ME!", "WOOF WOOF!", "WOOF!");

        //  Puts all Quiz objects into a list (global variable quizList)
        quizList = new Quiz[]{quiz0, quiz1, quiz2, quiz3, quiz4, quiz5, quiz6, quiz7, quiz8, quiz9};
    }

    /**
     *  The generateNewQuestion method generates a random number between 0 and the length of the
     *  variable quizList. This random number will be used to pick a Quiz object from quizArrayList
     *  (defined below) in order to populate the user's screen.
     *
     *  This method also populates the user screen.
     */
    public void generateNewQuestion() {
        //  Generate a random number between 0 and the number of Quiz objects to randomly collect a Quiz object from the "database" (quizList)
        Random random = new Random();
        int position = random.nextInt(quizList.length);

        //  Pass the correct answer to a String variable to check correct answer later
        correctAnswer = quizList[position].getAnswerCorrect();

        //  Pass all answers to an ArrayList
        final ArrayList<String> answersArrayList = new ArrayList<>(Arrays.asList(quizList[position].getAnswerCorrect(),quizList[position].getAnswerWrong1(),
                quizList[position].getAnswerWrong2(),quizList[position].getAnswerWrong3()));

        //  Create an ArrayList that will be used to inflate the LisView (used with ArrayAdapter)
        final ArrayList<String> listViewArrayList = new ArrayList<String>();

        //  Transfer answers from answersArrayList to listViewArrayList in random positions
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(answersArrayList.size());
            listViewArrayList.add(answersArrayList.get(j));
            answersArrayList.remove(j);
        }

        //  Create and inflate a ListView on the screen
        answersListView = (ListView) findViewById(R.id.answersListView);
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        questionTextView.setText(quizList[position].getQuestion());
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listViewArrayList);
        answersListView.setAdapter(adapter);

        //  Handle the click/selection of ListView items
        answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Pass selected String value to userAnswer and call method checkCorrectAnswer
                userAnswer = (String) parent.getItemAtPosition(position);
                checkCorrectAnswer();
            }
        });
    }

    /**
     *  This method compares both userAnswer and correctAnswer values in order to
     *  check whether or not the user has selected the correct answer to the question.
     *
     *  It also updates userScore when the answer is correct.
     */
    public void checkCorrectAnswer() {
        if (userAnswer.equals(correctAnswer)) {
            Toast.makeText(getApplicationContext(), R.string.answer_correct, Toast.LENGTH_SHORT).show();
            userScore++;
        } else {
            Toast.makeText(MainActivity.this, R.string.answer_wrong, Toast.LENGTH_SHORT).show();
        }
        generateNewQuestion();
    }

    public void startCountDown() {
        final TextView timerTextView = (TextView) findViewById(R.id.timerTextView);

        new CountDownTimer(30000,1000 - 500) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(((millisUntilFinished / 1000) + 1)  + " seconds remaining");
            }

            public void onFinish() {
                // Do something
                Toast.makeText(MainActivity.this, "Time up!", Toast.LENGTH_SHORT).show();
                timerTextView.setText("0 seconds remaining");
            }
        }.start();
    }

}
