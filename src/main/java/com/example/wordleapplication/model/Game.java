package com.example.wordleapplication.model;

public class Game {
    private String word;
    private int currentTries;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCurrentTries() {
        return currentTries;
    }

    public void setCurrentTries(int currentTries) {
        this.currentTries = currentTries;
    }

    public Game(String word, int currentTries) {
        this.word = word;
        this.currentTries = currentTries;
    }

    public void remove(String userToken) {
    }
}
