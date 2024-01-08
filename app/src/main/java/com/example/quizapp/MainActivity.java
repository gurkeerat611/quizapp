package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button nextQuestionButton;
    private TextView feedbackTextView;
    private TextView correctAnswerTextView;
    private TextView explanationTextView;
    private ImageView relatedPhotoImageView;

    private String[] questions = {
            "What is the capital of Japan?",
            "In which year did World War II end?",
            "Which planet is known as the Red Planet?",
            "Who wrote the play 'Romeo and Juliet'?",
            "What is the largest mammal in the world?",
            "Which element has the chemical symbol 'O'?",
            "What is the currency of India?",
            "Who painted the Mona Lisa?",
            "Which programming language is known for its simplicity?",
            "What is the tallest mountain in the world?"
    };

    private String[] options = {
            "Tokyo", "Beijing", "Seoul", "Bangkok",
            "1945", "1939", "1941", "1943",
            "Mars", "Jupiter", "Venus", "Mercury",
            "William Shakespeare", "Jane Austen", "Charles Dickens", "Leo Tolstoy",
            "Blue Whale", "Elephant", "Giraffe", "Hippopotamus",
            "Oxygen", "Osmium", "Ozone", "Omnium",
            "Rupee", "Yen", "Rupiah", "Won",
            "Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Michelangelo",
            "Python", "Java", "C++", "Ruby",
            "Mount Everest", "Kangchenjunga", "K2", "Makalu"
    };

    private int[] correctOptionIndices = {
            0, // Capital of Japan
            0, // Year World War II ended
            2, // Red Planet is Mars
            0, // 'Romeo and Juliet' was written by William Shakespeare
            0, // Largest mammal is the Blue Whale
            0, // Oxygen has the chemical symbol 'O'
            1, // Currency of India is the Rupee
            0, // Mona Lisa was painted by Leonardo da Vinci
            1, // Python is known for its simplicity
            0  // Tallest mountain is Mount Everest
    };

    private String[] explanations = {
            "Tokyo is the capital of Japan.",
            "World War II ended in 1945.",
            "Mars is known as the Red Planet.",
            "William Shakespeare wrote the play 'Romeo and Juliet'.",
            "The Blue Whale is the largest mammal in the world.",
            "Oxygen has the chemical symbol 'O'.",
            "The currency of India is the Rupee.",
            "The Mona Lisa was painted by Leonardo da Vinci.",
            "Python is known for its simplicity in programming.",
            "Mount Everest is the tallest mountain in the world."
    };

    private String[] photoUrls = {
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/288px-Skyscrapers_of_Shinjuku_2009_January.jpg",
            "https://example.com/ww2_end_photo.jpg",
            "https://example.com/red_planet_photo.jpg",
            "https://example.com/romeo_juliet_photo.jpg",
            "https://example.com/blue_whale_photo.jpg",
            "https://example.com/oxygen_photo.jpg",
            "https://example.com/rupee_photo.jpg",
            "https://example.com/mona_lisa_photo.jpg",
            "https://example.com/python_photo.jpg",
            "https://example.com/mount_everest_photo.jpg"
    };

    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextQuestionButton = findViewById(R.id.nextQuestionButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        correctAnswerTextView = findViewById(R.id.correctAnswerTextView);
        explanationTextView = findViewById(R.id.explanationTextView);
        relatedPhotoImageView = findViewById(R.id.relatedPhotoImageView);

        displayQuestion();

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    displayQuestion();
                    resetButtons();
                } else {
                    feedbackTextView.setText("Quiz completed!");
                    disableButtons();
                    nextQuestionButton.setEnabled(false); // Disable Next Question button after quiz completion
                }
            }
        });
    }

    private void displayQuestion() {
        questionTextView.setText("Question: " + questions[currentQuestionIndex]);

        int startIndex = currentQuestionIndex * 4;
        option1Button.setText(options[startIndex]);
        option2Button.setText(options[startIndex + 1]);
        option3Button.setText(options[startIndex + 2]);
        option4Button.setText(options[startIndex + 3]);

        feedbackTextView.setText("");
        correctAnswerTextView.setText("");
        explanationTextView.setText("");

        // Load the photo related to the right answer using Picasso
        if (currentQuestionIndex < photoUrls.length) {
            Picasso.get().load(photoUrls[currentQuestionIndex]).into(relatedPhotoImageView);
        } else {
            relatedPhotoImageView.setImageResource(0);  // Clear the previous photo
        }
    }

    private void checkAnswer(int selectedOptionIndex) {
        if (selectedOptionIndex == correctOptionIndices[currentQuestionIndex]) {
            feedbackTextView.setText("Good!");
            correctAnswerTextView.setText("");
            explanationTextView.setText("");  // Clear the explanation
            nextQuestionButton.setEnabled(true); // Enable Next Question button
        } else {
            feedbackTextView.setText("Incorrect. Try again.");
            displayCorrectAnswer();
            nextQuestionButton.setEnabled(false); // Disable Next Question button
        }
    }

    private void displayCorrectAnswer() {
        int correctOptionIndex = correctOptionIndices[currentQuestionIndex];

        if (currentQuestionIndex * 4 + correctOptionIndex < options.length) {
            correctAnswerTextView.setText("Correct answer: " + options[currentQuestionIndex * 4 + correctOptionIndex]);
            explanationTextView.setText("Explanation: " + explanations[currentQuestionIndex]);

            // Load the photo related to the right answer using Picasso
            if (currentQuestionIndex < photoUrls.length) {
                Picasso.get().load(photoUrls[currentQuestionIndex]).into(relatedPhotoImageView);
            } else {
                relatedPhotoImageView.setImageResource(0);  // Clear the previous photo
            }
        } else {
            correctAnswerTextView.setText("Error: Correct answer not found");
        }
    }

    private void disableButtons() {
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }

    private void resetButtons() {
        option1Button.setEnabled(true);
        option2Button.setEnabled(true);
        option3Button.setEnabled(true);
        option4Button.setEnabled(true);
        nextQuestionButton.setEnabled(false);
    }
}
