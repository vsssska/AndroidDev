package com.example.lab5;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab5.databinding.ActivityQuizBinding;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private QuestionViewModel viewModel;
    private int currentQuestionIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        // setup bindings
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Подгружаем ВьюМодел
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this); // Устанавливаем LifecycleOwner для LiveData

        // Кнопка ответа
        binding.submitButton.setOnClickListener(v -> handleAnswer());

        // Кнопка назад
        binding.backButton.setOnClickListener(v -> onBackPressed());


        if (savedInstanceState == null) {
            // Загружаем фрагменты при первом запуске
            if (findViewById(R.id.fragment_stats_container) == null) {
                loadQuestionFragment(viewModel.getCurrentIndex().getValue());
            } else {
                // Горизонтальная ориентация: фрагменты для вопроса и статистики
                loadQuestionFragment(viewModel.getCurrentIndex().getValue());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_stats_container, new StatsFragment())
                        .commit();
            }
        }
    }

    // Кнопка ответа
    private void handleAnswer() {
        // Проверка ответа и обновление статистики
        checkAnswerForCurrentQuestion();

        if (currentQuestionIndex < viewModel.getQuestions().size() - 1) {
            currentQuestionIndex++;
            viewModel.savecurrentIndex(currentQuestionIndex);
            loadQuestionFragment(viewModel.getCurrentIndex().getValue());
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
            loadQuestionFragment(viewModel.getCurrentIndex().getValue());
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
}