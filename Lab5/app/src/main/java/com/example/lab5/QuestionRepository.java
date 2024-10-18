package com.example.lab5;
import java.util.List;  // Для списков
import java.util.ArrayList;  // Для Array списков
public class QuestionRepository {
    private List<Question> questionList;

    public QuestionRepository() {
        questionList = new ArrayList<>();

        // Вопрос с одним вариантом ответа
        questionList.add(new Question("Какая платформа используется для разработки Android приложений?",
                Question.TYPE_SINGLE_CHOICE,
                new String[]{"Xcode", "Visual Studio", "Android Studio", "Eclipse"},
                "Android Studio"));

        // Вопрос с несколькими вариантами ответа
        questionList.add(new Question("Какие из следующих языков используются для разработки Android приложений?",
                Question.TYPE_MULTIPLE_CHOICE,
                new String[]{"Java", "Kotlin", "Swift", "C++"},
                new String[]{"Java", "Kotlin"}));

        // Вопрос со свободным ответом
        questionList.add(new Question("Как называется язык разметки для интерфейсов Android?",
                Question.TYPE_TEXT_ANSWER,
                null,
                "XML"));

        // Вопрос с изображением
        questionList.add(new Question("Что изображено на картинке?",
                Question.TYPE_IMAGE_QUESTION,
                R.drawable.android_logo,
                "Android"));
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}

