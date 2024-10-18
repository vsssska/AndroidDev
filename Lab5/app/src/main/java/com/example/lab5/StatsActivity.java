package com.example.lab5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    private TextView statsTextView;
    private Button shareButton;
    private Button moreInfoButton;

    private int correctAnswers;
    private int incorrectAnswers;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        statsTextView = findViewById(R.id.statsTextView);
        shareButton = findViewById(R.id.shareButton);
        moreInfoButton = findViewById(R.id.moreInfoButton);

        // Получаем данные из MainActivity
        Intent intent = getIntent();
        correctAnswers = intent.getIntExtra("correctAnswers", 0);
        incorrectAnswers = intent.getIntExtra("incorrectAnswers", 0);
        totalQuestions = intent.getIntExtra("totalQuestions", 0);

        setStatsTextView();

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
    }

    public void setStatsTextView(){
        // Отображаем статистику
        String stats = "Правильные ответы: " + correctAnswers + "\n" +
                "Неправильные ответы: " + incorrectAnswers + "\n" +
                "Всего вопросов: " + totalQuestions;
        statsTextView.setText(stats);
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
