package com.modashmo.nhlquizz;

/**
 * NHL Quizz
 * Created by Mauricio on April 26, 2017
 * based on tutorials, examples found in the web
 * and feedback from experienced friend/developer.
 * <p>
 * Udacity's Android Basics Nanodegree
 * Project 3: Quiz App
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Question> questionList;        //  List that holds Question instances (database)
    Question currentQuestion;           //  Instance of Question shown on screen and being worked by the app
    List<Answer> answerArrayList;       //  List that holds all Answer to currentQuestion
    Answer userAnswer;                  //  Answer chosen by the user, from answerArrayList
    CheckBox[] checkBoxesAnswers;       //  List that holds all CheckBox to populate the screen when questionType is Multiple Answers
    EditText editTextAnswer;            //  EditText to populate screen when questionType is Text Answers
    int userScore = 0;                  //  User score starting at zero, hardcoded :(

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame(R.string.game_start);
    }

    /**
     * The method startGame will prompt a dialogue to inform the user that the game is about to start.
     * Upon interaction, checks if the database questionList (hardcoded on method addQuizDatabase) is null,
     * initiating method addQuizDatabase if the return is true.
     * <p>
     * It also starts the game by calling method generateNewQuestion.
     *
     * @param message: Message to prompt the user
     */
    private void startGame(int message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //  Check if null and initialise quiz database: see method documentation below
                        if (questionList == null) {
                            addQuizDatabase();
                        }
                        //  Generate a new question and populate screen when app is initialised
                        generateNewQuestion();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     * The generateNewQuestion method generates a {@link Random} number between 0 and the length of the
     * array questionList. This random number will be used to pick a Question object from questionList
     * in order to properly populate the users screen.
     */
    public void generateNewQuestion() {
        //  Generate a random number between 0 and the number of Question instances to randomly collect a Question object from the "database" (questionList)
        Random random = new Random();
        int position = random.nextInt(questionList.size());
        //  Initialise currentQuestion according to the random number
        currentQuestion = questionList.get(position);
        //  Declare and initialise an ArrayList of Answer and transfer all Answer instances from currentQuestion
        answerArrayList = new ArrayList<>();
        for (int i = 0; i < currentQuestion.answers.size(); i++) {
            answerArrayList.add(currentQuestion.answers.get(i));
        }
        //  Show question on user screen with a TextView
        TextView questionTextView = (TextView) findViewById(R.id.question_text_view);
        questionTextView.setText(currentQuestion.getQuestion());
        //  Shuffle answerArrayList positions
        Collections.shuffle(answerArrayList);
        //  Check if single, multiple or text answer in order to load correct screen components
        switch (checkQuestionType(currentQuestion)) {
            case 1:
                loadCheckBoxScreen();
                break;
            case 2:
                loadRadioGroupScreen();
                break;
            case 3:
                loadEditTextScreen();
                break;
        }
    }

    /**
     * Method checkQuestionType will check the instance of {@link Question} in order to determine
     * if it is Multiple, Single or Text answer.
     *
     * @param question: Instance of Question class previously initialised
     */
    public byte checkQuestionType(Question question) {
        return question.questionType;
    }

    /**
     * Method loadCheckBoxScreen declares and initialises determined Views into a {@link LinearLayout}
     * when the Question instance was determined to be Multiple answers.
     */
    public void loadCheckBoxScreen() {
        checkBoxesAnswers = new CheckBox[answerArrayList.size()];
        LinearLayout layoutAnswers = (LinearLayout) findViewById(R.id.layout_answers);
        layoutAnswers.removeAllViews();
        //  Dynamically create CheckBox according to the number of Answer and populates them accordingly
        for (int i = 0; i < checkBoxesAnswers.length; i++) {
            checkBoxesAnswers[i] = new CheckBox(MainActivity.this);
            checkBoxesAnswers[i].setText(answerArrayList.get(i).answer);
            layoutAnswers.addView(checkBoxesAnswers[i]);
        }
    }

    /**
     * Method loadRadioGroupScreen declares and initialises determined Views into a {@link LinearLayout}
     * when the Question instance was determined to be Single answer.
     */
    public void loadRadioGroupScreen() {
        final RadioButton[] radioButtonsAnswers = new RadioButton[answerArrayList.size()];
        RadioGroup radioGroup = new RadioGroup(MainActivity.this);
        LinearLayout layoutAnswers = (LinearLayout) findViewById(R.id.layout_answers);
        layoutAnswers.removeAllViews();
        layoutAnswers.addView(radioGroup);
        //  Dynamically create RadioButton according to the number of Answer and populates them accordingly
        for (int i = 0; i < answerArrayList.size(); i++) {
            radioButtonsAnswers[i] = new RadioButton(MainActivity.this);
            radioGroup.addView(radioButtonsAnswers[i]);
            radioButtonsAnswers[i].setText(answerArrayList.get(i).answer);
        }
        //  OnCheckedChangeListener will listen to user's selection and pass the correct Answer as
        //  value to variable userAnswer
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId if the selected RadioButton
                for (int i = 0; i < radioButtonsAnswers.length; i++) {
                    if (checkedId == radioButtonsAnswers[i].getId()) {
                        userAnswer = answerArrayList.get(i);
                        break;
                    }
                }
            }
        });
    }

    /**
     * Method loadEditTextScreen declares and initialises determined Views into a {@link LinearLayout}
     * when the Question instance was determined to be Text answer.
     */
    public void loadEditTextScreen() {
        LinearLayout layoutAnswers = (LinearLayout) findViewById(R.id.layout_answers);
        layoutAnswers.removeAllViews();
        editTextAnswer = new EditText(MainActivity.this);
        editTextAnswer.setHint(R.string.answer_hint);
        editTextAnswer.setSingleLine();
        layoutAnswers.addView(editTextAnswer);
    }

    /**
     * Method checkCorrectMultiple will assess whether or not the user picked the correct
     * answers by comparing the boolean values between {@link CheckBox} and answerArrayList
     */
    public void checkCorrectMultiple() {
        if (answerArrayList.get(0).isCorrect == checkBoxesAnswers[0].isChecked() &&
                answerArrayList.get(1).isCorrect == checkBoxesAnswers[1].isChecked() &&
                answerArrayList.get(2).isCorrect == checkBoxesAnswers[2].isChecked() &&
                answerArrayList.get(3).isCorrect == checkBoxesAnswers[3].isChecked()) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Method checkCorrectSingle will assess whether or not the user picked the correct
     * answer by checking the parameter received
     *
     * @param answer: Passed from {@link RadioGroup} according to user's {@link RadioButton} input
     */
    public void checkCorrectSingle(Answer answer) {
        if (answer.isCorrect) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Method checkCorrectText will assess whether or not the user typed the correct
     * answer by checking the parameter received
     *
     * @param answer: Passed from {@link EditText} according to user's text input
     */
    public void checkCorrectText(String answer) {
        if (answer.equals(currentQuestion.answers.get(0).toString())) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Method submitAnswer checks whether or not the answer is correct, updating the score
     * afterwards (if necessary) and always calling generateNewQuestion on user's screen
     *
     * @param view: Basically the {@link android.widget.Button} handling clicks
     */
    public void submitAnswer(View view) {
        switch (checkQuestionType(currentQuestion)) {
            case 1:
                checkCorrectMultiple();
                generateNewQuestion();
                break;
            case 2:
                if (userAnswer != null) {
                    checkCorrectSingle(userAnswer);
                    generateNewQuestion();
                } else
                    Toast.makeText(this, "Please make a selection", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                String userTextAnswer = editTextAnswer.getText().toString();
                checkCorrectText(userTextAnswer);
                break;
        }
    }

    /**
     * The correctAnswer method updates userScore and displays userScore in a Toast message.
     * <p>
     * It also restarts the game by generating a new question (generateNewQuestion)
     */
    public void correctAnswer() {
        userScore++;
        Toast.makeText(this, "Correct! Your score is: " + String.valueOf(userScore), Toast.LENGTH_SHORT).show();
        generateNewQuestion();
    }

    /**
     * The incorrectAnswer method does not update userScore and displays userScore in a Toast message.
     * <p>
     * It also restarts the game by generating a new question (generateNewQuestion)
     */
    public void incorrectAnswer() {
        Toast.makeText(this, "Try again! Your score is: " + String.valueOf(userScore), Toast.LENGTH_SHORT).show();
        generateNewQuestion();
    }

    /**
     * The addQuizDatabase method is a void type method that create Question objects
     * to be populated on the screen. Ideally this method should pull data from
     * an SQLite database, JSON or RESTful API.
     * <p>
     * For this project, I'm manually initiating the objects since it does not
     * require any of the storage methods above mentioned, however I can't stress
     * enough that a real app would pull data in a different (smarter) way.
     */
    public void addQuizDatabase() {
        //  The code below initiates as many Question objects as we need for the app
        Question question0 = new Question("Who was the Detroit Red Wings captain before Steve Yzerman?", (byte) 2);
        question0.addAnswer("Reed Larson", false);
        question0.addAnswer("Danny Gare", true);
        question0.addAnswer("Mike O'Connel", false);
        question0.addAnswer("Mike Foligno", false);

        Question question1 = new Question("Tim Horton had his jersey retired. With which team/s was it retired?", (byte) 1);
        question1.addAnswer("Buffalo Sabres", true);
        question1.addAnswer("Los Angeles Kings", false);
        question1.addAnswer("Winnipeg Jets", false);
        question1.addAnswer("Toronto Maple Leafs", true);

        Question question2 = new Question("Who was the first Edmonton Oiler to have his jersey retired?", (byte) 2);
        question2.addAnswer("Lee Folingo", false);
        question2.addAnswer("Al Hamilton", true);
        question2.addAnswer("Wayne Gretzky", false);
        question2.addAnswer("Nail Yakupov", false);

        Question question3 = new Question("Which of these teams has Wayne Gretzky played?", (byte) 1);
        question3.addAnswer("Edmonton Oilers", true);
        question3.addAnswer("Los Angeles Kings", true);
        question3.addAnswer("St. Louis Blues", true);
        question3.addAnswer("Toronto Maple Leafs", false);

        Question question4 = new Question("Which of these teams are NOT part of the Original Six?", (byte) 1);
        question4.addAnswer("Winnipeg Jets", true);
        question4.addAnswer("Detroit Red Wings", false);
        question4.addAnswer("New York Islanders", true);
        question4.addAnswer("Montreal Canadiens", false);

        Question question5 = new Question("Who was Winnipeg Jets 2nd overall pick in the NHL 2016 Draft?", (byte) 2);
        question5.addAnswer("Patrick Laine", true);
        question5.addAnswer("Jacob Trouba", false);
        question5.addAnswer("Jonathan Toews", false);
        question5.addAnswer("Nikolaj Ehlers", false);

        Question question6 = new Question("Which of these teams has Bobby Orr NOT played?", (byte) 1);
        question6.addAnswer("Boston Bruins", false);
        question6.addAnswer("Chicago Blackhawks", false);
        question6.addAnswer("San Jose Sharks", true);
        question6.addAnswer("Nordiques de Quebec", true);

        Question question7 = new Question("Where was Jaromir Jagr born?", (byte) 2);
        question7.addAnswer("Czechoslovakia", true);
        question7.addAnswer("Russia", false);
        question7.addAnswer("USA", false);
        question7.addAnswer("Belarus", false);

        Question question8 = new Question("What is the name of Winnipeg's NHL team?", (byte) 3);
        question8.addAnswer("Jets", true);

        Question question9 = new Question("What city is home for the Arizona Coyotes?", (byte) 3);
        question9.addAnswer("Glendale", true);

        //  Initiates the ArrayList with all Question instances
        questionList = new ArrayList<>(Arrays.asList(question0, question1, question2, question3, question4, question5, question6, question7, question8, question9));
    }

}