package com.example.lab11;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {
    private final List<Integer> targetSequence = new ArrayList<>();
    public final MutableLiveData<List<String>> attempts = new MutableLiveData<>(new ArrayList<>());
    public final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>(false);
    public final MutableLiveData<String> resultMessage = new MutableLiveData<>("");
    private final int maxAttempts = 15;
    private int attemptCount = 0;

    public GameViewModel() {
        generateTargetSequence();
    }

    private void generateTargetSequence() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        targetSequence.clear();
        targetSequence.addAll(numbers.subList(0, 4));
    }

    public void submitGuess(String guess) {
        if (isGameOver.getValue() != null && isGameOver.getValue()) return;

        attemptCount++;
        List<Integer> guessNumbers = new ArrayList<>();
        for (char ch : guess.toCharArray()) {
            guessNumbers.add(Character.getNumericValue(ch));
        }

        int phases = 0, peaks = 0;
        for (int i = 0; i < guessNumbers.size(); i++) {
            if (guessNumbers.get(i).equals(targetSequence.get(i))) {
                phases++;
            } else if (targetSequence.contains(guessNumbers.get(i))) {
                peaks++;
            }
        }

        String attemptResult = "Попытка " + attemptCount + ": " + guess + " — " +
                peaks + " пики, " + phases + " фазы.";
        List<String> currentAttempts = new ArrayList<>(attempts.getValue());
        currentAttempts.add(attemptResult);
        attempts.setValue(currentAttempts);

        if (phases == 4) {
            resultMessage.setValue("Вы выиграли! Последовательность: " + targetSequence);
            isGameOver.setValue(true);
        } else if (attemptCount >= maxAttempts) {
            resultMessage.setValue("Вы проиграли. Последовательность: " + targetSequence);
            isGameOver.setValue(true);
        }
    }

    public void finishGame() {
        isGameOver.setValue(true);
        resultMessage.setValue("Игра завершена. Последовательность: " + targetSequence);
    }
}
