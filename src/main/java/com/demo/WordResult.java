package com.demo;

public class WordResult {
    private int score;
    private String word;

    public WordResult(int score, String word) {
        this.score = score;
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
