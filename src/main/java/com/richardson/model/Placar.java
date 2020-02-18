package com.richardson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.richardson.util.DateDeserializer;
import com.richardson.util.DateSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "placarPista")
@JsonPropertyOrder({ "alley", "lastGame", "beginGame", "players" })
public class Placar {
    
    @Id
    private String alley;
    
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty(value = "last_game")
    @JsonSerialize(using = DateSerializer.class)
    private Date lastGame;
    
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty(value = "begin_game")
    @JsonSerialize(using = DateSerializer.class)
    private Date beginGame;
    
    private List<Jogador> players = new ArrayList<>();
    
    public String getAlley() {
        return alley;
    }
    
    public void setAlley(String alley) {
        this.alley = alley;
    }
    
    public Date getLastGame() {
        return lastGame;
    }
    
    public void setLastGame(Date lastGame) {
        this.lastGame = lastGame;
    }
    
    public Date getBeginGame() {
        return beginGame;
    }
    
    public void setBeginGame(Date beginGame) {
        this.beginGame = beginGame;
    }
    
    public List<Jogador> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<Jogador> players) {
        this.players = players;
    }
    
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
