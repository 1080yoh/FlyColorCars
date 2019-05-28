package pes.ralter.flycolorcars.model;

import android.os.Bundle;

public class Score {
    private int id;
    private int score;
    private String difficult;

    public Score(int id, int score, String difficult) {
        this.id = id;
        this.score = score;
        this.difficult = difficult;
    }

    public Score(int score, String difficult) {
        this.score = score;
        this.difficult = difficult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }
}
