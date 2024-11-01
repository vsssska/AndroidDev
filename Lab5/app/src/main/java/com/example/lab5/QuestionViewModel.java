package com.example.lab5;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel extends ViewModel {
    private List<Question> questions = new ArrayList<>();
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int currentIndex = 0;

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

}
