package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleChoiceQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleChoiceQuestionFragment extends Fragment {

    private Question question;
    private String userAnswer;
    private RadioGroup optionsGroup;

    // возвращаем ответ
    public String getUserAnswer() {
        return userAnswer;
    }

    public static SingleChoiceQuestionFragment newInstance(Question question) {
        SingleChoiceQuestionFragment fragment = new SingleChoiceQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_choice_question, container, false);

        TextView questionTextView = view.findViewById(R.id.questionTextView);
        optionsGroup = view.findViewById(R.id.singleChoiceGroup);

        question = (Question) getArguments().getSerializable("question");

        questionTextView.setText(question.getQuestionText());
        optionsGroup.removeAllViews();

        // Создаем RadioButton для каждого варианта ответа
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(option);
            optionsGroup.addView(radioButton); // Добавляем RadioButton в RadioGroup
        }

        // Устанавливаем слушатель для RadioGroup
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedOption = view.findViewById(checkedId);
            this.userAnswer = selectedOption.getText().toString(); // сохраняем ответ пользователя
        });

        return view;
    }
}