package com.richardson.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.richardson.util.DateDeserializer;

import java.util.Date;

@JsonPropertyOrder({ "name", "pins", "alley", "date" })
public class Jogada {
    private String name;
    private Integer pins;
    private String alley;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPins() {
        return pins;
    }
    
    public void setPins(Integer pins) {
        this.pins = pins;
    }
    
    public String getAlley() {
        return alley;
    }
    
    public void setAlley(String alley) {
        this.alley = alley;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
