package com.richardson.service;

import com.richardson.exception.LancesExcedidosException;
import com.richardson.model.Jogada;
import com.richardson.model.Jogador;
import com.richardson.model.Lance;
import com.richardson.model.Placar;
import com.richardson.repository.BolicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
public class BolicheService {
    
    @Autowired
    private BolicheRepository bolicheRepository;
    
    public Placar efetuarJogada(Jogada jogada) {
        Placar placar = this.buscaPista(jogada.getAlley());
        
        if (Objects.isNull(placar)) {
            placar = this.iniciaNovoPlacar(jogada);
            return this.bolicheRepository.insert(placar);
        }
        
        if (Objects.isNull(placar.getBeginGame()))
            placar.setBeginGame(jogada.getDate());
        
        Jogador jogador = this.localizaJogador(placar, jogada.getName());
        
        if (Objects.isNull(jogador)) {
            placar.getPlayers().add(this.montaJogador(jogada));
            return this.bolicheRepository.save(placar);
            
        }
    
        Lance lanceAnterior = jogador.getFrames().get(jogador.getFrames().size() - 1);
        
        if (jogador.getFrames().size() == 10) { // última jogada, podem ser até 3 bolas
            if (jogador.getFrames().get(9).getBalls().size() == 3)
                throw new LancesExcedidosException("Não pode mais efetuar jogadas em nome de: ".concat(jogada.getName()));
            
            // 1ª bola de até 3
            if (lanceAnterior.getBalls().size() == 0 ||
                    lanceAnterior.getBalls().size() == 3) {
                Integer scoreAtual = lanceAnterior.getScore();
                jogador.getFrames().add(this.montaNovoLance(jogada, scoreAtual));
            } else {
                Lance lanceNovo = this.acresceJogadaAoLance(jogada, lanceAnterior);
                jogador.getFrames().remove(jogador.getFrames().size() - 1);
                jogador.getFrames().add(lanceNovo);
            }
        } else { // jogada normal, até 2 bolas
            // 1ª bola
            if (lanceAnterior.getBalls().size() == 0 ||
                    lanceAnterior.getBalls().size() == 2 ||
                    (lanceAnterior.getBalls().size() == 1 && lanceAnterior.getBalls().get(0) == 10)) {
                Integer scoreAtual = lanceAnterior.getScore();
                jogador.getFrames().add(this.montaNovoLance(jogada, scoreAtual));
            } else {
                Lance lanceNovo = this.acresceJogadaAoLance(jogada, lanceAnterior);
                jogador.getFrames().remove(jogador.getFrames().size() - 1);
                jogador.getFrames().add(lanceNovo);
            }
        }
        
        return this.bolicheRepository.save(placar);
    }
    
    public Placar zerarPista(String alley) {
        Calendar c = Calendar.getInstance();
        Placar placar = this.bolicheRepository.findByAlleyNumber(alley);
        placar.setLastGame(c.getTime());
        placar.setBeginGame(null);
        placar.setPlayers(null);
        
        return this.bolicheRepository.save(placar);
    }
    
    private Placar buscaPista(String alley) {
        return this.bolicheRepository.findByAlleyNumber(alley);
    }
    
    public Placar scorePista(String alley) { //throws Exception {
        Placar placar = this.bolicheRepository.findByAlleyNumber(alley);
        
        placar.getPlayers().forEach(jogador -> {
            jogador.setFrames(this.calculaScore(jogador.getFrames()));
        });
        
        return placar;
    }
    
    private Placar iniciaNovoPlacar(Jogada jogada) {
        Placar placar = new Placar();
        placar.setBeginGame(jogada.getDate());
        placar.setAlley(jogada.getAlley());
        placar.getPlayers().add(this.montaJogador(jogada));
        
        return placar;
    }
    
    private Jogador localizaJogador(Placar placar, String nome) {
        return placar.getPlayers().stream().filter(j -> j.getName().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }
    
    private Jogador montaJogador(Jogada jogada) {
        Jogador jogador = new Jogador();
        jogador.setName(jogada.getName());
        jogador.getFrames().add(this.montaNovoLance(jogada, 0));
        
        return jogador;
    }
    
    
    private Lance montaNovoLance(Jogada jogada, Integer scoreAtual) {
        Lance lance = new Lance();
        lance.getBalls().add(jogada.getPins());
        lance.setScore(scoreAtual + jogada.getPins());
        
        return lance;
    }
    
    private Lance acresceJogadaAoLance(Jogada jogada, Lance lance) {
        Lance lanceModificado = new Lance();
        
        lanceModificado.setBalls(lance.getBalls());
        lanceModificado.getBalls().add(jogada.getPins());
        lanceModificado.setScore(lance.getScore() + jogada.getPins());
        
        return lanceModificado;
    }
    
    private Boolean verificaStrike(Lance lance) {
        return lance.getBalls().stream().anyMatch(integer -> integer == 10);
    }
    
    private Boolean verificaSpare(Lance lance) {
        return (lance.getBalls().size() == 2 && (lance.getBalls().get(0) + lance.getBalls().get(1) == 10) ||
                lance.getBalls().size() == 3 && (lance.getBalls().get(0) + lance.getBalls().get(1) == 10 ||
                                                lance.getBalls().get(1) +lance.getBalls().get(2) == 10));
    }
    
    private List<Lance> calculaScore(List<Lance> lanceList) {
        List<Lance> lances = new ArrayList<>();
        Integer scoreAtual = 0;
        
        for (Lance l : lanceList) {
            Lance lance = new Lance();
            if (lanceList.indexOf(l) == 8) { // penúltimo lance
                if (this.verificaStrike(l)) { // Se fiz strike, preciso somar as próximas duas bolas do último lance
                    Lance l1 = lanceList.get(lanceList.indexOf(l) + 1);
                    scoreAtual += this.somaPinos(l, Boolean.FALSE) + l1.getBalls().get(0) + l1.getBalls().get(1);
                    lance.setScore(scoreAtual);
                }
            } else if (lanceList.indexOf(l) == 9) {
                scoreAtual += this.somaPinos(l, Boolean.FALSE);
                lance.setScore(scoreAtual);
            } else {
                if (this.verificaStrike(l)) { // Se fiz strike, preciso ver as próximas duas bolas do próximo lance
                    Lance l1 = lanceList.get(lanceList.indexOf(l) + 1);
                    if (this.verificaStrike(l1)) { // Se fiz strike novamente, preciso ver a próxima jogada do próximo lance
                        Lance l2 = lanceList.get(lanceList.indexOf(l) + 2);
                        if (this.verificaStrike(l2)) { // Mais um strike, então é o primeiro + o segundo lances + a primerira bola do terceiro lance
                            scoreAtual += this.somaPinos(l, Boolean.FALSE) + this.somaPinos(l1, Boolean.FALSE) + l2.getBalls().get(0);
                            lance.setScore(scoreAtual);
                        }
                    } else if (this.verificaSpare(l1)) { // Fiz um spare
                        scoreAtual += this.somaPinos(l, Boolean.FALSE) + this.somaPinos(l1, Boolean.TRUE);
                        lance.setScore(scoreAtual);
                    } else {
                        scoreAtual += this.somaPinos(l, Boolean.FALSE);
                        lance.setScore(scoreAtual);
                    }
                } else if (this.verificaSpare(l)) {
                    Lance l1 = lanceList.get(lanceList.indexOf(l) + 1);
                    scoreAtual += this.somaPinos(l, Boolean.FALSE) + this.somaPinos(l1, Boolean.TRUE);
                    lance.setScore(scoreAtual);
                } else { // jogada normal
                    scoreAtual += this.somaPinos(l, Boolean.FALSE);
                    lance.setScore(scoreAtual);
                }
            }
            
            lance.setBalls(l.getBalls());
            lances.add(lance);
            System.out.println(lance);
        }
        
        return lances;
    }
    
    private Integer somaPinos(Lance lance, Boolean isSpare) {
        Integer soma = 0;
        for (Integer pinos : lance.getBalls()) {
            soma += pinos;
            if (isSpare)
                break;
        }
        
        return soma;
    }
}
