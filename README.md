# Controlador de pontos - Jogos de Boliche

<p align="center"><img src="https://jogarboliche.com.br/wp-content/uploads/2018/09/como-jogar-boliche-score.jpg" /></p>

O contador de pontos de um jogador no [jogo de Boliche](https://jogarboliche.com.br/como-jogar-boliche/) consiste em 10 quadros, como mostrado acima. Em cada quadro o jogador possui duas oportunidades de derrubar 10 pinos. A pontuação para o quadro é o número total de pinos derrubados, além de bônus por strikes e spares.

Um spare é quando o jogador derruba todos os 10 pinos em duas tentativas. O bônus para esse quadro é o número de pinos derrubados na próxima jogada. Então no quadro 1 da jogadora Trace acima, a pontuação é 10 (o número total derrubado) mais um bônus de 8 (o número de pinos derrubados na próxima jogada.)

Um strike é quando o jogador derruba todos os 10 pinos em sua primeira tentativa. O bônus para esse quadro é o valor das próximas duas bolas jogadas.

No décimo quadro, um jogador que faz um spare ou strike pode rolar bolas extra para completar o quadro. No entanto, não mais que três bolas podem ser jogadas no décimo quadro.

## Desafio

Criar uma solução para controlador os pontos dos jogos de boliche utilizando as tecnologias Java 17, Spring Boot, Junit e Maven, com três integrações:

1. Receber, através de uma interface REST em formato JSON, as informações da jogada: quantos pinos um jogador derrubou; e em qual pista ele esta jogando:
```python
POST http://[host]:[port]/game/[alley]/score
```
```javascript
{"name":"João", "pins":6, "alley":"01"}
```
2. Disponibilizar uma interface REST que retorna o placar atual do jogo em andamento em uma das pistas:
```python
GET http://[host]:[port]/game/[alley]/score
```
```javascript
{ 
    "alley":"01",
    "players":[ 
        { 
            "name":"João",
            "frames":[ 
                { 
                    "balls":[1,4],
                    "score":5
                },
                { 
                    "balls":[4,5],
                    "score":14
                },
                { 
                    "balls":[6,4],
                    "score":29
                },
                { 
                    "balls":[5,5],
                    "score":49
                },
                { 
                    "balls":[10],
                    "score":60
                },
                { 
                    "balls":[0,1],
                    "score":61
                },
                { 
                    "balls":[7,3],
                    "score":76
                },
                { 
                    "balls":[6,4],
                    "score":97
                },
                { 
                    "balls":[10],
                    "score":117
                },
                { 
                    "balls":[2,8,6],
                    "score":133
                }
            ]
        },
        { 
            "name":"Maria",
            "frames":[ 
                { 
                    "balls":[1,4],
                    "score":5
                },
                { 
                    "balls":[4,5],
                    "score":14
                },
                { 
                    "balls":[6,4],
                    "score":29
                },
                { 
                    "balls":[5,5],
                    "score":49
                },
                { 
                    "balls":[10],
                    "score":60
                },
                { 
                    "balls":[10],
                    "score":60
                },
                { 
                    "balls":[0,1],
                    "score":61
                },
                { 
                    "balls":[7,3],
                    "score":76
                },
                { 
                    "balls":[6,4],
                    "score":97
                },
                { 
                    "balls":[10],
                    "score":107
                }
            ]
        }
    ]
}
```
3. Disponibilizar uma interface REST para zerar/limpar o jogo atual na pista:
```python
DELETE http://[host]:[port]/game/[alley]
```
## Requisitos funcionais:

- [ ] **RF1.** Ao receber a primeira jogada para uma pista que não existia, essa nova pista deve ser criada.
- [ ] **RF2.** Ao receber as informações da jogada a pontação do quadro deve ser calculado conforme regras do jogo. Caso a pontuação do quadro dependa das próximas bolas jogadas o campo score deve mostra a pontuação parcial.
- [ ] **RF3.** Jogadas recebidas para um jogador que ultrapassem o limite estabelecido pelo jogo devem ser descartadas.
- [ ] **RF4.** Quando um jogo for zerado/reiniado a pista deve ser apagada.

## Requisitos não funcionais obrigatórios:

- [ ] **RNF01.** O estado atual das pistas, bem como a respectivas pontuações de cada jogador, devem ser persistidas.
- [ ] **RNF02.** O projeto deve ter testes automatizados.

## Requisitos não funcionais desejáveis:

- [ ] **RND01.** Solução totalmente em Cloud
- [ ] **RND02.** Execução em Docker/Kubernetes
- [ ] **RND03.** Utilização de banco de dados NoSQL
- [ ] **RND04.** Divisão da solução em microsserviços

## Instruções gerais

- Faça um fork desse repositório e efetue um pull request para entrega.
- É de livre escolha qual o banco de dados será utilizado.
- Para entregas que necessite de uma execução local, crie um setup.md informando todos os passos para criação dos objetos, configuração e execução para submeter os testes e validação da solução. Lembrando que solução é Spring Boot.
- Para entregas em Cloud informar as URLs de acesso, usários e senhas (caso necessário).
