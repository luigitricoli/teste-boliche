package com.richardson.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Lance {
    private List<Integer> balls = new ArrayList<>(); // pins?
    private Integer score = 0;
    
    public List<Integer> getBalls() {
        return balls;
    }
    
    public void setBalls(List<Integer> balls) {
        this.balls = balls;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
