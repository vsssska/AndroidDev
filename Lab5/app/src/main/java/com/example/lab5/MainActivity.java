package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;



public class MainActivity extends AppCompatActivity {

    private QuestionViewModel viewModel;
    private Button submitButton;
    private Button backButton;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // УДАЛИТЬ ПАТОМ
//        questionTextView = findViewById(R.id.questionTextView);
//        statsTextView = findViewById(R.id.statsTextView);
//        singleChoiceGroup = findViewById(R.id.singleChoiceGroup);
//        multipleChoiceLayout = findViewById(R.id.multipleChoiceLayout);
//        textAnswerEditText = findViewById(R.id.textAnswerEditText);
//        imageView = findViewById(R.id.imageView);
        // УДАЛИТЬ ПАТОМ

        // Подгружаем ВьюМодел
        //viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        QuestionRepository repository = new QuestionRepository();
        questionList = repository.getQuestionList(); // Добавляем ответы из вью модел

        // Кнопка ответа
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> handleAnswer());

        // Кнопка назад
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        if (savedInstanceState == null) {
            loadQuestionFragment(0);
        }

    }

    private void handleAnswer() {
        // Проверка ответа и обновление статистики
        checkAnswerForCurrentQuestion();

        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            //viewModel.savecurrentIndex(currentQuestionIndex);
            loadQuestionFragment(currentQuestionIndex);
        } else {
            Toast.makeText(this, "Вы прошли все вопросы!", Toast.LENGTH_SHORT).show();
            // Показать результаты (другая активность)
            showStatsScreen();
        }
    }

    // Показать экран со статистикой (другую активность)
    private void showStatsScreen() {
        Intent intent = new Intent(MainActivity.this, StatsActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("incorrectAnswers", incorrectAnswers);
        intent.putExtra("totalQuestions", questionList.size());
        startActivity(intent);
    }

    private void loadQuestionFragment(int index) {
        Question question = questionList.get(currentQuestionIndex);
        Fragment fragment = null;

        Bundle bundle = new Bundle();
        bundle.putSerializable("question", question);

        switch (question.getType()) {
            case Question.TYPE_SINGLE_CHOICE:
                fragment = new SingleChoiceQuestionFragment();
                fragment.setArguments(bundle);

                break;
            case Question.TYPE_MULTIPLE_CHOICE:
                fragment = new MultipleChoiceQuestionFragment();
                fragment.setArguments(bundle);
                break;
            case Question.TYPE_TEXT_ANSWER:
                fragment = new TextAnswerFragment();
                fragment.setArguments(bundle);
                break;
            case Question.TYPE_IMAGE_QUESTION:
                fragment = new TextAnswerFragment();
                fragment.setArguments(bundle);
                break;
            default:
                throw new IllegalArgumentException("Unknown question type");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void checkAnswerForCurrentQuestion() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof SingleChoiceQuestionFragment) {
            // Получение ответа из фрагмента одиночного выбора
            SingleChoiceQuestionFragment singleChoiceFragment = (SingleChoiceQuestionFragment) currentFragment;
            String answer = singleChoiceFragment.getUserAnswer();

            // Проверка ответа
            if (answer != null && answer.equals("asdfsaf")) {
                correctAnswers++;
            } else {
                incorrectAnswers++;
            }
        }
        else if (currentFragment instanceof MultipleChoiceQuestionFragment) {
// Получение ответа из фрагмента множественного выбора
            MultipleChoiceQuestionFragment multipleChoiceQuestionFragment = (MultipleChoiceQuestionFragment) currentFragment;
            String answer = multipleChoiceQuestionFragment.getUserAnswer();

            // Проверка ответа
            if (answer != null && answer.equals("asdfsaf")) {
                correctAnswers++;
            } else {
                incorrectAnswers++;
            }
        }
        else if (currentFragment instanceof TextAnswerFragment) {
            // Получение ответа из сво бодного ответа
            TextAnswerFragment textAnswerFragment = (TextAnswerFragment) currentFragment;
            String answer = textAnswerFragment.getUserAnswer();

            // Проверка ответа
            if (answer != null && answer.equals("asdfsaf")) {
                correctAnswers++;
            } else {
                incorrectAnswers++;
            }
        }

    }



//    private void showNextQuestion() {
//        Question currentQuestion = questionList.get(currentQuestionIndex);
//        questionTextView.setText(currentQuestion.getQuestionText());
//        Fragment questionFragment;
//
//        // Скрываем все виды вопросов
//        singleChoiceGroup.setVisibility(View.GONE);
//        multipleChoiceLayout.setVisibility(View.GONE);
//        textAnswerEditText.setVisibility(View.GONE);
//        imageView.setVisibility(View.GONE);
//
//        // Для смены привязки у EditText
//        ConstraintLayout.LayoutParams params;
//
//        // Для вывода информации о статистике ответов
//        // Отображаем статистику
//        String stats = "Правильные ответы: " + correctAnswers + "\n" +
//                "Неправильные ответы: " + incorrectAnswers + "\n";
//        statsTextView.setText(stats);
//
//        switch (currentQuestion.getType()) {
//            case Question.TYPE_SINGLE_CHOICE:
//                // Настраиваем варианты ответов для RadioButton
//                singleChoiceGroup.setVisibility(View.VISIBLE);
//                for (String option : currentQuestion.getOptions()) {
//                    RadioButton radioButton = new RadioButton(this);
//                    radioButton.setText(option);
//                    singleChoiceGroup.addView(radioButton); // Добавляем RadioButton в RadioGroup
//                }
//                break;
//            case Question.TYPE_MULTIPLE_CHOICE:
    //                multipleChoiceLayout.setVisibility(View.VISIBLE);
    //                // Настраиваем варианты ответов для CheckBox
    //                for (String option : currentQuestion.getOptions()) {
    //                    CheckBox checkBox = new CheckBox(this);
    //                    checkBox.setText(option);
    //                    checkBoxes.add(checkBox); // Добавляем CheckBox в список
    //                    multipleChoiceLayout.addView(checkBox); // Добавляем CheckBox в контейнер
    //                }
//                break;
//            case Question.TYPE_TEXT_ANSWER:
//                textAnswerEditText.setVisibility(View.VISIBLE);
//                // Получаем текущие параметры Layout
//                params = (ConstraintLayout.LayoutParams) textAnswerEditText.getLayoutParams();
//
//                // Устанавливаем новую связь для layout_constraintTop_toBottomOf
//                params.topToBottom = R.id.questionTextView;  // ID элемента, под которым должно быть расположено ваше view
//
//                // Применяем обновленные параметры обратно к View
//                textAnswerEditText.setLayoutParams(params);
//                break;
//            case Question.TYPE_IMAGE_QUESTION:
//                textAnswerEditText.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setImageResource(currentQuestion.getImageResId());
//                // Получаем текущие параметры Layout
//                params = (ConstraintLayout.LayoutParams) textAnswerEditText.getLayoutParams();
//
//                // Устанавливаем новую связь для layout_constraintTop_toBottomOf
//                params.topToBottom = R.id.imageView;  // ID элемента, под которым должно быть расположено ваше view
//
//                // Применяем обновленные параметры обратно к View
//                textAnswerEditText.setLayoutParams(params);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown question type");
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_container, questionFragment);
//        if (currentQuestionIndex > 0) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commit();
//    }

//    private void checkAnswer() {
//        if(currentQuestionIndex >= questionList.size()){
//            currentQuestionIndex = 0;
//            correctAnswers = 0;
//            incorrectAnswers = 0;
//            return;
//        }
//        Question currentQuestion = questionList.get(currentQuestionIndex);
//        String userAnswer = ""; // Собираем ответ пользователя в зависимости от типа вопроса
//
//        // Ищем правильный ответ
//        switch (currentQuestion.getType()) {
//            case Question.TYPE_SINGLE_CHOICE:
//                int selectedRadioButtonId = singleChoiceGroup.getCheckedRadioButtonId();
//
//                if (selectedRadioButtonId != -1) {
//                    // Получаем выбранный RadioButton по его ID
//                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
//                    // Получаем текст выбранного RadioButton
//                    userAnswer = selectedRadioButton.getText().toString();
//
//                } else {
//                    Toast.makeText(this, "Ничего не выбрано", Toast.LENGTH_SHORT).show();
//                }
//                singleChoiceGroup.removeAllViews();
//                break;
//            case Question.TYPE_MULTIPLE_CHOICE:
//                // Проходимся по чекбоксам и собираем выбранные
//                for (CheckBox checkBox : checkBoxes) {
//                    if (checkBox.isChecked()) {
//                        userAnswer.concat(checkBox.getText().toString());
//                    }
//                }
//                multipleChoiceLayout.removeAllViews();
//                break;
//            case Question.TYPE_TEXT_ANSWER:
//                userAnswer = textAnswerEditText.getText().toString();
//                break;
//            case Question.TYPE_IMAGE_QUESTION:
//                textAnswerEditText.setVisibility(View.VISIBLE);
//                userAnswer = textAnswerEditText.getText().toString();
//                break;
//        }
//
//        // Проверяем правильность ответа
//        if (userAnswer.equals(currentQuestion.getCorrectAnswer())) {
//            correctAnswers++;
//        } else {
//            incorrectAnswers++;
//        }
//    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //viewModel.savecurrentIndex(currentQuestionIndex);
    }

    @Override
    public void onBackPressed() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestionFragment(currentQuestionIndex);
        } else {
            super.onBackPressed();
        }
    }
}
