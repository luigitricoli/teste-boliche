package com.richardson.application;

import com.richardson.model.Jogada;
import com.richardson.model.Placar;
import com.richardson.service.BolicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BolicheApplicationImpl implements BolicheApplication {
    
    @Autowired
    private BolicheService bolicheService;
    
    @Override
    public Placar efetuarJogada(Jogada jogada) {
        return this.bolicheService.efetuarJogada(jogada);
    }
    
    @Override
    public Placar zerarPista(String alley) {
        return this.bolicheService.zerarPista(alley);
    }
    
    @Override
    public Placar scorePista(String alley) {
        return this.bolicheService.scorePista(alley);
    }
}
