package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.lab5.QuestionRepository;
import com.example.lab5.Question;
import android.view.View;  // Для работы с View
import android.widget.Button;  // Для кнопок
import android.widget.EditText;  // Для текстовых полей
import android.widget.ImageView;  // Для изображений
import android.widget.LinearLayout;  // Для LinearLayout
import android.widget.RadioGroup;  // Для RadioGroup
import android.widget.TextView;  // Для TextView
import android.widget.Toast;  // Для всплывающих сообщений
import androidx.appcompat.app.AppCompatActivity;  // Для работы с Activity
import java.util.List;  // Для списков


public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup singleChoiceGroup;
    private LinearLayout multipleChoiceLayout;
    private EditText textAnswerEditText;
    private ImageView imageView;
    private Button submitButton;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        singleChoiceGroup = findViewById(R.id.singleChoiceGroup);
        multipleChoiceLayout = findViewById(R.id.multipleChoiceLayout);
        textAnswerEditText = findViewById(R.id.textAnswerEditText);
        imageView = findViewById(R.id.imageView);
        submitButton = findViewById(R.id.submitButton);

        QuestionRepository repository = new QuestionRepository();
        questionList = repository.getQuestionList();

        showNextQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    showNextQuestion();
                } else {
                    // Показать результаты
                    Toast.makeText(MainActivity.this, "Игра окончена. Правильных: " + correctAnswers + ", Неправильных: " + incorrectAnswers, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showNextQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestionText());

        // Скрываем все виды вопросов
        singleChoiceGroup.setVisibility(View.GONE);
        multipleChoiceLayout.setVisibility(View.GONE);
        textAnswerEditText.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        switch (currentQuestion.getType()) {
            case Question.TYPE_SINGLE_CHOICE:
                singleChoiceGroup.setVisibility(View.VISIBLE);
                // Настраиваем варианты ответов
                break;
            case Question.TYPE_MULTIPLE_CHOICE:
                multipleChoiceLayout.setVisibility(View.VISIBLE);
                // Настраиваем варианты ответов
                break;
            case Question.TYPE_TEXT_ANSWER:
                textAnswerEditText.setVisibility(View.VISIBLE);
                break;
            case Question.TYPE_IMAGE_QUESTION:
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(currentQuestion.getImageResId());
                break;
        }
    }

    private void checkAnswer() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        String userAnswer = ""; // Собираем ответ пользователя в зависимости от типа вопроса

        // Проверяем правильность ответа
        if (userAnswer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
        } else {
            incorrectAnswers++;
        }
    }
}
