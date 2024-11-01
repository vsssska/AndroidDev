package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultipleChoiceQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultipleChoiceQuestionFragment extends Fragment {

    private Question question;
    private String userAnswer;
    private List<String> userAnswers = new ArrayList<>();
    private LinearLayout multipleChoiceLayout;
    private List<CheckBox> checkBoxes = new ArrayList<>();

    // возвращаем ответ
    public String getUserAnswer() {

        StringBuilder result = new StringBuilder(); // Используем StringBuilder для сборки строки

        for (String string : userAnswers) {
            result.append(string);
        }

        return result.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);

        TextView questionTextView = view.findViewById(R.id.questionTextView);
        multipleChoiceLayout = view.findViewById(R.id.multipleChoiceLayout);
        question = (Question) getArguments().getSerializable("question");

        questionTextView.setText(question.getQuestionText());
        multipleChoiceLayout.removeAllViews();

        multipleChoiceLayout.setVisibility(View.VISIBLE);
        // Настраиваем варианты ответов для CheckBox
        for (String option : question.getOptions()) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(option);
            checkBoxes.add(checkBox); // Добавляем CheckBox в список
            multipleChoiceLayout.addView(checkBox); // Добавляем CheckBox в контейнер

            // Устанавливаем слушатель для каждого CheckBox
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    userAnswers.add(buttonView.getText().toString()); // Добавляем выбранный ответ
                } else {
                    userAnswers.remove(buttonView.getText().toString()); // Убираем, если ответ отменен
                }
            });
        }

        return view;
    }
}