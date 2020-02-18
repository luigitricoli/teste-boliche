package com.richardson.controller;

import com.richardson.application.BolicheApplication;
import com.richardson.model.Jogada;
import com.richardson.model.Placar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/game")
@Api(value = "Boliche", description = "Controlador de Jogadas de Boliche")
public class BolicheController {

    @Autowired
    private BolicheApplication bolicheApplication;
    
    @ApiOperation(value = "efetuarJogada", nickname = "Registrar jogada")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> efetuarJogada(@RequestBody @Valid Jogada jogada) throws Exception {
        Placar placar = this.bolicheApplication.efetuarJogada(jogada);
        return new ResponseEntity<>(placar, HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "zerarPista", nickname = "Reiniciar jogo na pista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @RequestMapping(value = "{alley}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> zerarPista(@PathVariable String alley) throws Exception {
        this.bolicheApplication.zerarPista(alley);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiOperation(value = "scorePista", nickname = "Retorna o placar da pista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure", response = Exception.class)})
    @RequestMapping(value = "/{alley}/score", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> busca(@PathVariable String alley) {
        final Placar placar = this.bolicheApplication.scorePista(alley);
        
        if (Objects.isNull(placar))
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        
        return new ResponseEntity<>(placar, HttpStatus.OK);
    }
}
