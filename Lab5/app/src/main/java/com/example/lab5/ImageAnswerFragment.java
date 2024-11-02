package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageAnswerFragment extends Fragment {

    private Question question;
    private String userAnswer;
    private EditText editText;
    private ImageView imageView;

    // возвращаем ответ
    public String getUserAnswer() {
        userAnswer = editText.getText().toString();
        return userAnswer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_answer, container, false);
        question = (Question) getArguments().getSerializable("question");

        TextView questionTextView = view.findViewById(R.id.questionTextView);
        questionTextView.setText(question.getQuestionText());

        imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(question.getImageResId());

        editText = view.findViewById(R.id.textAnswerEditText);


        return view;


    }
}