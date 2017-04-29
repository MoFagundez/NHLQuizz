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
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Question> questionList;
    ListView answersListView;
    String userAnswer;
    String correctAnswer;
    int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame(R.string.game_start);
    }

    /**
     *  Documentation pending...
     */
    private void startGame(int message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
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
     *  The addQuizDatabase method is a void type method that create Question objects
     *  to be populated on the screen. Ideally this method should pull data from
     *  an SQLite database, JSON or RESTful API.
     *
     *  For this project, I am manually initiating the objects since it does not
     *  require any of the storage methods above mentioned.
    * */
    public void addQuizDatabase() {
        //  The code below initiates as many Question objects as we need for the app
        Question question0 = new Question("E ai coleh que eh?");
        question0.addAnswer("Fogo na bomba", true);
        question0.addAnswer("Cara de bunda", false);
        question0.addAnswer("Feia jagunca", false);
        question0.addAnswer("Serviu a carapuca", false);

        Question question1 = new Question("Quem eh Tripa Seca?");
        question1.addAnswer("Seu Madruga", true);
        question1.addAnswer("Veia da verruga", false);
        question1.addAnswer("Na bunda urtiga", false);
        question1.addAnswer("Fera ferida", false);

        Question question2 = new Question("Qual Leonardo eh o famoso inventor da palavra 'bundapum'?");
        question2.addAnswer("Fagundes", true);
        question2.addAnswer("Da Vinci", false);
        question2.addAnswer("Di Caprio", false);
        question2.addAnswer("O irmao do Leandro", false);

        Question question3 = new Question("Com quantos paus se faz uma canoa?");
        question3.addAnswer("Depende do tamanho", true);
        question3.addAnswer("Hoje em dia de fibra", false);
        question3.addAnswer("Quatorze ou Catorze?", false);
        question3.addAnswer("A canoa virou...", false);

        Question question4 = new Question("Quem bateu no pirulito que bate-bate?");
        question4.addAnswer("Ela, que gosta de mim", true);
        question4.addAnswer("A torcida do Corinthians", false);
        question4.addAnswer("Rocky Balboa", false);
        question4.addAnswer("A mae dele pois ele eh sapeca", false);

        questionList = new ArrayList<>(Arrays.asList(question0, question1, question2, question3, question4));

    }

    /**
     *  The generateNewQuestion method generates a random number between 0 and the length of the
     *  variable questionList. This random number will be used to pick a Quiz object from quizArrayList
     *  (defined below) in order to populate the user's screen.
     *
     *  This method also populates the user screen.
     */
    public void generateNewQuestion() {
        //  Generate a random number between 0 and the number of Quiz objects to randomly collect a Quiz object from the "database" (questionList)
        Random random = new Random();
        int position = random.nextInt(questionList.size());

        //  Create an ArrayList that will be used to inflate the LisView (used with ArrayAdapter)
        final ArrayList<Answer> listViewArrayList = new ArrayList<>();

        //  Transfer answers from answersArrayList to listViewArrayList in random positions
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(questionList.get(position).answers.size());
            listViewArrayList.add(questionList.get(position).answers.get(j));
            questionList.get(position).answers.remove(j);
        }

        //  Create and inflate a ListView on the screen
        answersListView = (ListView) findViewById(R.id.answersListView);
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        questionTextView.setText(questionList.get(position).getQuestion());
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listViewArrayList);
        answersListView.setAdapter(adapter);

        //  Handle the click/selection of ListView items
        answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //checkCorrectAnswer();
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

    /**
     *  Documentation pending...
     */
    public void startCountDown() {
        final TextView timerTextView = (TextView) findViewById(R.id.timerTextView);

        new CountDownTimer(5000,1000 - 500) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(((millisUntilFinished / 1000) + 1)  + " seconds remaining");
            }

            public void onFinish() {
                // Do something
                timerTextView.setText("0 seconds remaining");
                startGame(R.string.game_restart_suffix);
            }
        }.start();
    }

}
