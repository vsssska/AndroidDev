package com.example.lab5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StatsFragment extends Fragment {

    private QuestionViewModel viewModel;
    private TextView statsTextView;
    private Button shareButton;
    private Button moreInfoButton;

    private int correctAnswers;
    private int incorrectAnswers;
    private int totalQuestions;


    public StatsFragment() {
        // Required empty public constructor
    }

    public void setStatsTextView(int correctAnswers, int incorrectAnswers, int totalQuestions){
        // Отображаем статистику
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.totalQuestions = totalQuestions;

        String stats = "Правильные ответы: " + correctAnswers + "\n" +
                "Неправильные ответы: " + incorrectAnswers + "\n" +
                "Всего вопросов: " + totalQuestions;
        statsTextView.setText(stats);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        statsTextView = view.findViewById(R.id.statsTextView);
        shareButton = view.findViewById(R.id.shareButton);
        moreInfoButton = view.findViewById(R.id.moreInfoButton);

        // Кнопка поделиться статистикой
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareStats();
            }
        });

        // Кнопка открыть сайт
        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoreInfo();
            }
        });


        return view;
    }

    public void updateInfo(){

    }

    private void shareStats() {
        String stats = "Я прошел тест по теме разработки приложений на Anroid! " +
                "Правильные ответы: " + correctAnswers + ", " +
                "Неправильные ответы: " + incorrectAnswers + ", " +
                "Всего вопросов: " + totalQuestions;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, stats);
        startActivity(Intent.createChooser(shareIntent, "Поделиться статистикой через"));
    }

    private void openMoreInfo() {
        // URL сайта с информацией
        String url = "https://example.com/more-info";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}