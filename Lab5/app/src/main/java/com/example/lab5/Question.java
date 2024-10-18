package com.example.lab5;

public class Question {
    public static final int TYPE_SINGLE_CHOICE = 1;
    public static final int TYPE_MULTIPLE_CHOICE = 2;
    public static final int TYPE_TEXT_ANSWER = 3;
    public static final int TYPE_IMAGE_QUESTION = 4;

    private String questionText;
    private int type;
    private String[] options;
    private String correctAnswer;
    private int imageResId;

    // Конструктор для обычных вопросов
    public Question(String questionText, int type, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Конструктор для вопросов с изображением
    public Question(String questionText, int type, int imageResId, String correctAnswer) {
        this.questionText = questionText;
        this.type = type;
        this.imageResId = imageResId;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getType() {
        return type;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getImageResId() {
        return imageResId;
    }
}

