package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.RadioButton; // Для Radio Button
import android.widget.CheckBox; // Для Check Box
import java.util.ArrayList;  // Для Array списков
import androidx.appcompat.app.AppCompatActivity;  // Для работы с Activity
import java.util.List;  // Для списков
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView statsTextView;
    private RadioGroup singleChoiceGroup;
    private LinearLayout multipleChoiceLayout;
    private EditText textAnswerEditText;
    private ImageView imageView;
    private Button submitButton;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int currentQuestionIndex = 0;
    private ConstraintLayout constraintLayout;
    private List<Question> questionList;
    List<CheckBox> checkBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Проверяем ориентацию экрана при запуске
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            launchStatsActivityInSplitScreen();
//        }

        questionTextView = findViewById(R.id.questionTextView);
        statsTextView = findViewById(R.id.statsTextView);
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
                    showStatsScreen();
                }
            }
        });
    }

    // Показать экран со статистикой
    private void showStatsScreen() {
        Intent intent = new Intent(MainActivity.this, StatsActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("incorrectAnswers", incorrectAnswers);
        intent.putExtra("totalQuestions", questionList.size());
        startActivity(intent);
    }

//    // Метод для запуска второго Activity в режиме Split-Screen
//    private void launchStatsActivityInSplitScreen() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            if (isInMultiWindowMode()) {
//                return; // Уже в режиме мультиоконности
//            }
//            // Запускаем StatsActivity
//            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            startActivity(intent);
//        }
//    }

//    // Метод когда экран перевернут
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Если устройство перевернуто в горизонтальную ориентацию
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            launchStatsActivityInSplitScreen();
//        }
//    }

    private void showNextQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestionText());

        // Скрываем все виды вопросов
        singleChoiceGroup.setVisibility(View.GONE);
        multipleChoiceLayout.setVisibility(View.GONE);
        textAnswerEditText.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        // Для смены привязки у EditText
        ConstraintLayout.LayoutParams params;

        // Для вывода информации о статистике ответов
        // Отображаем статистику
        String stats = "Правильные ответы: " + correctAnswers + "\n" +
                "Неправильные ответы: " + incorrectAnswers + "\n";
        statsTextView.setText(stats);

        switch (currentQuestion.getType()) {
            case Question.TYPE_SINGLE_CHOICE:
                // Настраиваем варианты ответов для RadioButton
                singleChoiceGroup.setVisibility(View.VISIBLE);
                for (String option : currentQuestion.getOptions()) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(option);
                    singleChoiceGroup.addView(radioButton); // Добавляем RadioButton в RadioGroup
                }
                break;
            case Question.TYPE_MULTIPLE_CHOICE:
                multipleChoiceLayout.setVisibility(View.VISIBLE);
                // Настраиваем варианты ответов для CheckBox
                for (String option : currentQuestion.getOptions()) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(option);
                    checkBoxes.add(checkBox); // Добавляем CheckBox в список
                    multipleChoiceLayout.addView(checkBox); // Добавляем CheckBox в контейнер
                }
                break;
            case Question.TYPE_TEXT_ANSWER:
                textAnswerEditText.setVisibility(View.VISIBLE);
                // Получаем текущие параметры Layout
                params = (ConstraintLayout.LayoutParams) textAnswerEditText.getLayoutParams();

                // Устанавливаем новую связь для layout_constraintTop_toBottomOf
                params.topToBottom = R.id.questionTextView;  // ID элемента, под которым должно быть расположено ваше view

                // Применяем обновленные параметры обратно к View
                textAnswerEditText.setLayoutParams(params);
                break;
            case Question.TYPE_IMAGE_QUESTION:
                textAnswerEditText.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(currentQuestion.getImageResId());
                // Получаем текущие параметры Layout
                params = (ConstraintLayout.LayoutParams) textAnswerEditText.getLayoutParams();

                // Устанавливаем новую связь для layout_constraintTop_toBottomOf
                params.topToBottom = R.id.imageView;  // ID элемента, под которым должно быть расположено ваше view

                // Применяем обновленные параметры обратно к View
                textAnswerEditText.setLayoutParams(params);
                break;
        }
    }

    private void checkAnswer() {
        if(currentQuestionIndex >= questionList.size()){
            currentQuestionIndex = 0;
            correctAnswers = 0;
            incorrectAnswers = 0;
            return;
        }
        Question currentQuestion = questionList.get(currentQuestionIndex);
        String userAnswer = ""; // Собираем ответ пользователя в зависимости от типа вопроса

        // Ищем правильный ответ
        switch (currentQuestion.getType()) {
            case Question.TYPE_SINGLE_CHOICE:
                int selectedRadioButtonId = singleChoiceGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId != -1) {
                    // Получаем выбранный RadioButton по его ID
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    // Получаем текст выбранного RadioButton
                    userAnswer = selectedRadioButton.getText().toString();

                } else {
                    Toast.makeText(this, "Ничего не выбрано", Toast.LENGTH_SHORT).show();
                }
                singleChoiceGroup.removeAllViews();
                break;
            case Question.TYPE_MULTIPLE_CHOICE:
                // Проходимся по чекбоксам и собираем выбранные
                for (CheckBox checkBox : checkBoxes) {
                    if (checkBox.isChecked()) {
                        userAnswer.concat(checkBox.getText().toString());
                    }
                }
                multipleChoiceLayout.removeAllViews();
                break;
            case Question.TYPE_TEXT_ANSWER:
                userAnswer = textAnswerEditText.getText().toString();
                break;
            case Question.TYPE_IMAGE_QUESTION:
                textAnswerEditText.setVisibility(View.VISIBLE);
                userAnswer = textAnswerEditText.getText().toString();
                break;
        }

        // Проверяем правильность ответа
        if (userAnswer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
        } else {
            incorrectAnswers++;
        }
    }


}
