package com.example.lab11;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab11.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация DataBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация ViewModel
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this); // Устанавливаем LifecycleOwner для LiveData

        // Обработка кнопок через DataBinding
        binding.btnSubmit.setOnClickListener(v -> {
            String guess = binding.etGuess.getText().toString();
            if (guess.length() != 4) {
                Toast.makeText(this, "Введите последовательность из 4 цифр", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.submitGuess(guess);
            binding.etGuess.setText("");
        });

        //binding.btnFinish.setOnClickListener(v -> viewModel.finishGame());
    }
}
