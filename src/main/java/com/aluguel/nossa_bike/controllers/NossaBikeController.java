package com.aluguel.nossa_bike.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.services.*;

@RestController
@RequestMapping("/")
public class NossaBikeController {

    @Autowired
    private CiclistaRepository dbCiclista;

    @Autowired
    private CiclistaService cicService;

    @PostMapping("/ciclista")
    public ResponseEntity<List<String>> cadastrar(@RequestBody Ciclista ciclista) {
        List<String> erros = cicService.cadastrar(ciclista);
        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(erros, HttpStatus.CREATED);
    }

    @GetMapping("/ciclista")
    public ResponseEntity<List<Ciclista>> ola() {
        return new ResponseEntity<>(dbCiclista.findByNome("Thiago"), HttpStatus.OK);
    }

    @PutMapping("/ciclista/{idCiclista}")
    public ResponseEntity<List<String>> accountEdit(@PathVariable UUID idCiclista, @RequestBody Ciclista ciclista) {
        List<String> erros = cicService.editAccount(idCiclista, ciclista);
        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(erros, HttpStatus.CREATED);
    }

    @PostMapping("/ciclista/{idCiclista}/ativar")
    public ResponseEntity<Ciclista> accountActivation(@PathVariable UUID idCiclista) {
        Ciclista ciclista = cicService.activateAccount(idCiclista);
        if (!(ciclista == null)) {
            return new ResponseEntity<Ciclista>(ciclista, HttpStatus.OK);
        } else {
            return new ResponseEntity<Ciclista>(ciclista, HttpStatus.NOT_FOUND);
        }
    }
}
