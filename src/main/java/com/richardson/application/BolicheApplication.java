package com.richardson.application;

import com.richardson.model.Jogada;
import com.richardson.model.Placar;

public interface BolicheApplication {
    
    Placar efetuarJogada(Jogada jogada);
    Placar zerarPista(String alley);
    Placar scorePista(String alley);
    
}
