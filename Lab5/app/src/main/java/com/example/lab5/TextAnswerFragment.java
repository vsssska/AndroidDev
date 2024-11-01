package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


public class TextAnswerFragment extends Fragment {

    private Question question;
    private String userAnswer;
    private EditText editText;

    // возвращаем ответ
    public String getUserAnswer() {
        userAnswer = editText.getText().toString();
        return userAnswer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text_answer, container, false);

        TextView questionTextView = view.findViewById(R.id.questionTextView);

        question = (Question) getArguments().getSerializable("question");
        editText = view.findViewById(R.id.textAnswerEditText);


        return view;


    }
}