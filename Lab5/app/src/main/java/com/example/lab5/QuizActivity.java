package com.example.lab5;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        // Подгружаем ВьюМодел
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        // Кнопка ответа
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> handleAnswer());

        // Кнопка назад
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());


        if (savedInstanceState == null) {
            // Загружаем фрагменты при первом запуске
            if (findViewById(R.id.fragment_stats_container) == null) {
                loadQuestionFragment(viewModel.getcurrentIndex());
            } else {
                // Горизонтальная ориентация: фрагменты для вопроса и статистики
                loadQuestionFragment(viewModel.getcurrentIndex());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_stats_container, new StatsFragment())
                        .commit();
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Кнопка ответа
    private void handleAnswer() {
        // Проверка ответа и обновление статистики
        checkAnswerForCurrentQuestion();

        if (currentQuestionIndex < viewModel.getQuestions().size() - 1) {
            currentQuestionIndex++;
            viewModel.savecurrentIndex(currentQuestionIndex);
            loadQuestionFragment(viewModel.getcurrentIndex());
            Log.i("Button press", "submitbutton");
            boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
            if (isLandscape) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_stats_container);
                currentFragment = (StatsFragment) currentFragment;
                ((StatsFragment) currentFragment).setStatsTextView(viewModel.getCorrectAnswerCount(),
                        viewModel.getQuestions().size()-viewModel.getCorrectAnswerCount(),
                        viewModel.getQuestions().size());
            }

        } else {
            Toast.makeText(this, "Вы прошли все вопросы!", Toast.LENGTH_SHORT).show();
            // Показать результаты (другая активность)
            showStatsScreen();
        }
    }

    // Показать экран со статистикой (другую активность)
    private void showStatsScreen() {
        Intent intent = new Intent(QuizActivity.this, StatsActivity.class);
        intent.putExtra("correctAnswers", viewModel.getCorrectAnswerCount());
        intent.putExtra("incorrectAnswers", viewModel.getQuestions().size()-viewModel.getCorrectAnswerCount());
        intent.putExtra("totalQuestions", viewModel.getQuestions().size());
        startActivity(intent);
    }

    // Загрузка каждого вопроса отдельным фрагментом
    private void loadQuestionFragment(int index) {
        Question question = viewModel.getQuestions().get(currentQuestionIndex);
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
                fragment = new ImageAnswerFragment();
                fragment.setArguments(bundle);
                break;
            default:
                throw new IllegalArgumentException("Unknown question type");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_quest_container, fragment)
                .commit();
    }

    // Проверка правильности
    private void checkAnswerForCurrentQuestion() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quest_container);
        String correctanswer = viewModel.getQuestions().get(viewModel.getcurrentIndex()).getCorrectAnswer();

        if (currentFragment instanceof SingleChoiceQuestionFragment) {
            // Получение ответа из фрагмента одиночного выбора
            SingleChoiceQuestionFragment singleChoiceFragment = (SingleChoiceQuestionFragment) currentFragment;
            String answer = singleChoiceFragment.getUserAnswer();

            viewModel.saveAnswer(currentQuestionIndex, answer);

        }
        else if (currentFragment instanceof MultipleChoiceQuestionFragment) {
// Получение ответа из фрагмента множественного выбора
            MultipleChoiceQuestionFragment multipleChoiceQuestionFragment = (MultipleChoiceQuestionFragment) currentFragment;
            String answer = multipleChoiceQuestionFragment.getUserAnswer();

            viewModel.saveAnswer(currentQuestionIndex, answer);

        }
        else if (currentFragment instanceof TextAnswerFragment) {
            // Получение ответа из сво бодного ответа
            TextAnswerFragment textAnswerFragment = (TextAnswerFragment) currentFragment;
            String answer = textAnswerFragment.getUserAnswer();

            viewModel.saveAnswer(currentQuestionIndex, answer);

        }
        else if (currentFragment instanceof ImageAnswerFragment) {
            // Получение ответа из сво бодного ответа с картинкой
            ImageAnswerFragment imageAnswerFragment = (ImageAnswerFragment) currentFragment;
            String answer = imageAnswerFragment.getUserAnswer();

            viewModel.saveAnswer(currentQuestionIndex, answer);

        }

    }

    // Кнопка "назад"
    @Override
    public void onBackPressed() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            viewModel.savecurrentIndex(currentQuestionIndex);
            loadQuestionFragment(viewModel.getcurrentIndex());
        } else {
            super.onBackPressed();
        }
    }

    // Сохранение инстанса
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        viewModel.savecurrentIndex(currentQuestionIndex);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_quiz);

        // Кнопка ответа
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> handleAnswer());

        // Кнопка назад
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Горизонтальная ориентация: фрагменты для вопроса и статистики
            loadQuestionFragment(viewModel.getcurrentIndex());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_stats_container, new StatsFragment())
                    .commit();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_stats_container);
            currentFragment = (StatsFragment) currentFragment;
            ((StatsFragment) currentFragment).setStatsTextView(viewModel.getCorrectAnswerCount(),
                    viewModel.getQuestions().size()-viewModel.getCorrectAnswerCount(),
                    viewModel.getQuestions().size());
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            // Портретная ориентация
            loadQuestionFragment(viewModel.getcurrentIndex());
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

        } }
}