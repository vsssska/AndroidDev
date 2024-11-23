package com.example.lab11;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameViewModel extends ViewModel {
    private final List<Integer> targetSequence = new ArrayList<>();
    public final MutableLiveData<List<String>> attempts = new MutableLiveData<>(new ArrayList<>());
    public final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>(false);
    public final MutableLiveData<String> resultMessage = new MutableLiveData<>("");
    private final int maxAttempts = 15;
    private int attemptCount = 0;
    private boolean allowRepeats; //тип последовательности

    public GameViewModel() {
        this(false); // По умолчанию последовательность без повторов
    }

    public GameViewModel(boolean allowRepeats) {
        this.allowRepeats = allowRepeats;
        generateTargetSequence();
    }

    public void setAllowRepeats(boolean allowRepeats) {
        this.allowRepeats = allowRepeats;
        generateTargetSequence(); // Перегенерировать последовательность при изменении параметра
        resetGame();
    }

    private void resetGame() {
        attemptCount = 0;
        attempts.setValue(new ArrayList<>());
        resultMessage.setValue("");
        isGameOver.setValue(false);
    }

    private void generateTargetSequence() {
        targetSequence.clear();

        if (allowRepeats) {
            generateWithRepeats();
        } else {
            generateWithoutRepeats();
        }
    }

    private void generateWithoutRepeats() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        targetSequence.addAll(numbers.subList(0, 4));
    }

    private void generateWithRepeats() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            targetSequence.add(random.nextInt(10)); // Случайное число от 0 до 9
        }
    }

    public void submitGuess(String guess) {
        if (isGameOver.getValue() != null && isGameOver.getValue()) return;

        attemptCount++;
        List<Integer> guessNumbers = new ArrayList<>();
        for (char ch : guess.toCharArray()) {
            guessNumbers.add(Character.getNumericValue(ch));
        }

        int phases = 0, peaks = 0;
        HashSet<Integer> usedIndices = new HashSet<>(); // Чтобы не дублировать пики
        for (int i = 0; i < guessNumbers.size(); i++) {
            if (guessNumbers.get(i).equals(targetSequence.get(i))) {
                phases++;
            } else if (targetSequence.contains(guessNumbers.get(i)) && !usedIndices.contains(guessNumbers.get(i))) {
                peaks++;
                usedIndices.add(guessNumbers.get(i));
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
