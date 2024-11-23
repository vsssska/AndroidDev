package com.example.lab11;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab11.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        AttemptAdapter adapter = new AttemptAdapter();
        binding.rvAttempts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAttempts.setAdapter(adapter);

        viewModel.attempts.observe(this, adapter::submitList);
        viewModel.resultMessage.observe(this, message -> {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.isGameOver.observe(this, isOver -> {
            if (isOver) {
                binding.btnSubmit.setEnabled(false);
            }
        });

        binding.btnSubmit.setOnClickListener(v -> {
            String guess = binding.etGuess.getText().toString();
            if (guess.length() != 4) {
                Toast.makeText(this, "Введите последовательность из 4 цифр", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.submitGuess(guess);
            binding.etGuess.setText("");
        });

        binding.btnFinish.setOnClickListener(v -> viewModel.finishGame());
        binding.switchAllowRepeats.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setAllowRepeats(isChecked); // Установить тип последовательности
            Toast.makeText(this, "Последовательность обновлена. Повторы " +
                    (isChecked ? "разрешены" : "запрещены"), Toast.LENGTH_SHORT).show();
            resetUI(); // Сброс состояния UI
        });
    }

    private void resetUI() {
        // Очистить поле ввода
        binding.etGuess.setText("");

        // Активировать кнопку "Отправить"
        binding.btnSubmit.setEnabled(true);

        // Очистить историю попыток
        viewModel.attempts.setValue(new ArrayList<>());

        // Сбросить текст результата
        viewModel.resultMessage.setValue("");

        // Убедиться, что игра не завершена
        viewModel.isGameOver.setValue(false);
    }

}
