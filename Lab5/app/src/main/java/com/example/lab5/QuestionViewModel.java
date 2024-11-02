package com.example.lab5;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionViewModel extends ViewModel {
    private List<Question> questions = new ArrayList<>();
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int currentIndex = 0;
    private final Map<Integer, String> answers = new HashMap<>();


    public QuestionViewModel() {
        QuestionRepository repository = new QuestionRepository();
        questions = repository.getQuestionList();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    public void incrementWrongAnswers() {
        wrongAnswers++;
    }

    public int getCorrectAnswers() {

        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void savecurrentIndex(int questionIndex) {
        currentIndex = questionIndex;
    }

    public int getcurrentIndex() {
        return currentIndex;
    }

    public void saveAnswer(int questionId, String answer) {
        answers.put(questionId, answer);
    }

    public int getCorrectAnswerCount() {
        int correctCount = 0;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            int questionId = entry.getKey();
            String userAnswer = entry.getValue();

            // Сравниваем с правильным ответом
            String correctAnswer = questions.get(questionId).getCorrectAnswer();
            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
                correctCount++;
            }

            Log.i("CorrectAnswerSave", correctAnswer);
        }
        return correctCount;
    }

}
