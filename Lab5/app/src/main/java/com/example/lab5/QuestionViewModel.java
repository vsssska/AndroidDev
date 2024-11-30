package com.example.lab5;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionViewModel extends ViewModel {
    private List<Question> questions = new ArrayList<>();
    private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>(0);
    private MutableLiveData<Integer> currentIndex = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> incorrectAnswers  = new MutableLiveData<>(0);
    private final Map<Integer, String> answers = new HashMap<>();


    public QuestionViewModel() {
        QuestionRepository repository = new QuestionRepository();
        questions = repository.getQuestionList();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public LiveData<Integer> getCurrentIndex() {
        return currentIndex;
    }

    public LiveData<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public LiveData<Integer> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void savecurrentIndex(int questionIndex) {
        currentIndex.setValue(questionIndex);
    }


    public void saveAnswer(int questionId, String answer) {
        answers.put(questionId, answer);
        int test = getCorrectAnswerCount();
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
        correctAnswers.setValue(correctCount);
        incorrectAnswers.setValue(questions.size()-correctCount);
        return correctCount;
    }

}
