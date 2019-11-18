package com.richardson.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String name;
    private List<Lance> frames = new ArrayList<>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Lance> getFrames() {
        return frames;
    }
    
    public void setFrames(List<Lance> frames) {
        this.frames = frames;
    }
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
