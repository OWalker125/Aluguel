package com.aluguel.nossa_bike.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aluguel.nossa_bike.models.*;
import com.aluguel.nossa_bike.repository.AluguelRepo;
import com.aluguel.nossa_bike.services.*;


@RestController
@RequestMapping("/")
public class NossaBikeController {

    private AluguelRepo dataBase = new AluguelRepo();
    private Cadastro cadastrador = new Cadastro();

    @PostMapping("/ciclista")
    public ResponseEntity<List<String>> cadastrar(@RequestBody Ciclista ciclista){
        List<String> erros = cadastrador.isValid(ciclista);
        if(!erros.isEmpty()){
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        cadastrador.sendVerificationMail(ciclista.getEmail());
        dataBase.save(ciclista);
        return new ResponseEntity<>(erros, HttpStatus.CREATED);
    }

    @GetMapping("/ciclista")
    public String ola(){
       return "Ola, mundo.";
    }
}
