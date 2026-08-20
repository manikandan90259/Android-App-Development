package com.example.quizapplication;// Change this to match your project's package name

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    private TextView tvProgress, tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnSubmit;

    // Quiz Data Arrays
    private String[] questions = {
            "Which planet is known as the Red Planet?",
            "What is the capital city of France?",
            "Who wrote the play 'Romeo and Juliet'?"
    };

    private String[][] options = {
            {"Earth", "Mars", "Jupiter", "Saturn"},
            {"London", "Berlin", "Paris", "Madrid"},
            {"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"}
    };

    private String[] correctAnswers = {
            "Mars",
            "Paris",
            "William Shakespeare"
    };

    // Tracking Variables
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);
        rgOptions = findViewById(R.id.rg_options);
        rbOption1 = findViewById(R.id.rb_option1);
        rbOption2 = findViewById(R.id.rb_option2);
        rbOption3 = findViewById(R.id.rb_option3);
        rbOption4 = findViewById(R.id.rb_option4);
        btnSubmit = findViewById(R.id.btn_submit);

        // Load the first question
        loadQuestion();

        // Handle Submit Button Click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if an option is selected
                int selectedId = rgOptions.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                } else {
                    // Find which radio button was clicked
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedAnswer = selectedRadioButton.getText().toString();

                    // Validate answer
                    if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                        score++;
                        Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong! Correct answer: " + correctAnswers[currentQuestionIndex], Toast.LENGTH_SHORT).show();
                    }

                    // Move to next question or finish quiz
                    currentQuestionIndex++;

                    if (currentQuestionIndex < questions.length) {
                        loadQuestion();
                    } else {
                        showFinalScore();
                    }
                }
            }
        });
    }

    // Method to populate text views with new question data
    private void loadQuestion() {
        rgOptions.clearCheck(); // Deselect all radio buttons

        tvProgress.setText("Question: " + (currentQuestionIndex + 1) + "/" + questions.length);
        tvQuestion.setText(questions[currentQuestionIndex]);
        rbOption1.setText(options[currentQuestionIndex][0]);
        rbOption2.setText(options[currentQuestionIndex][1]);
        rbOption3.setText(options[currentQuestionIndex][2]);
        rbOption4.setText(options[currentQuestionIndex][3]);
    }

    // Method to handle layout state when quiz ends
    private void showFinalScore() {
        tvProgress.setText("Quiz Completed!");
        tvQuestion.setText("Your Final Score is:\n" + score + " / " + questions.length);

        // Hide options and change submit button functionality
        rgOptions.setVisibility(View.GONE);
        btnSubmit.setText("Restart Quiz");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset state variables to restart quiz
                currentQuestionIndex = 0;
                score = 0;
                rgOptions.setVisibility(View.VISIBLE);
                btnSubmit.setText("Submit Answer");
                loadQuestion();

                // Re-bind the original click listener logic by recreating intents or recreating activity
                recreate();
            }
        });
    }
}