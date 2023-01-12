package com.aluguel.nossa_bike.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aluguel.nossa_bike.models.Cartao;
import com.aluguel.nossa_bike.models.Ciclista;
import com.aluguel.nossa_bike.models.Funcionario;
import com.aluguel.nossa_bike.models.AluguelStatus;
import com.aluguel.nossa_bike.models.AluguelStatus.Status;
import com.aluguel.nossa_bike.models.dtos.AluguelDTO;
import com.aluguel.nossa_bike.models.dtos.CicCartDTO;
import com.aluguel.nossa_bike.models.dtos.DevolucaoDTO;
import com.aluguel.nossa_bike.repository.CartaoRepository;
import com.aluguel.nossa_bike.repository.CiclistaRepository;
import com.aluguel.nossa_bike.repository.FuncionarioRepository;
import com.aluguel.nossa_bike.repository.RentRepository;
import com.aluguel.nossa_bike.services.*;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/")
public class NossaBikeController {

    @Autowired
    private CiclistaRepository dbCiclista;

    @Autowired
    private CiclistaService cicService;

    @Autowired
    private RentRepository dbRent;

    @Autowired
    private FuncionarioService funcService;

    @Autowired
    private FuncionarioRepository dbFunc;

    @Autowired
    private CartaoRepository dbCartao;

    @PostMapping("/ciclista")
    public ResponseEntity<List<String>> cadastrarCic(@RequestBody CicCartDTO cicCart) {
        List<String> erros = cicService.cadastrar(cicCart);
        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(erros, HttpStatus.CREATED);
    }

    @GetMapping("/ciclista")
    public ResponseEntity<List<Ciclista>> ola() {
        return new ResponseEntity<>(dbCiclista.findByNome("Thiago"), HttpStatus.OK);
    }

    @GetMapping("/ciclista/{idCiclista}")
    public ResponseEntity<Ciclista> getAccount(@PathVariable UUID idCiclista) {
        Ciclista account = dbCiclista.getByUuid(idCiclista);
        if (account == null) {
            return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/ciclista/{idCiclista}")
    public ResponseEntity<List<String>> accountEdit(@PathVariable UUID idCiclista, @RequestBody Ciclista ciclista) {
        List<String> erros = cicService.editAccount(idCiclista, ciclista);
        if (!erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(erros, HttpStatus.CREATED);
    }

    @GetMapping("/ciclista/{idCiclista}/permiteAluguel")
    public ResponseEntity<Boolean> isAluguelPermited(@PathVariable UUID idCiclista) {
        AluguelStatus rentRecord = dbRent.getByCiclista_Id(idCiclista);
        if (rentRecord == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        Status rentStatus = rentRecord.getRentStatus();
        return new ResponseEntity<>(rentStatus == Status.LIVRE, HttpStatus.OK);
    }

    @PutMapping("/ciclista/{idCiclista}/ativar")
    public ResponseEntity<Ciclista> accountActivation(@PathVariable UUID idCiclista) {
        Ciclista ciclista = cicService.activateAccount(idCiclista);
        if (!(ciclista == null)) {
            return new ResponseEntity<Ciclista>(ciclista, HttpStatus.OK);
        } else {
            return new ResponseEntity<Ciclista>(ciclista, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/funcionario")
    public ResponseEntity<List<String>> cadastrarFunc(@RequestBody Funcionario funcionario) {
        List<String> erros = funcService.cadastrar(funcionario);
        if (erros.isEmpty()) {
            return new ResponseEntity<>(erros, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/funcionario")
    public ResponseEntity<List<Funcionario>> listarFunc() {
        List<Funcionario> funcionarios = dbFunc.findAll();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @GetMapping("/funcionario/{matricula}")
    public ResponseEntity<Funcionario> buscarFunc(@PathVariable UUID matricula) {
        Funcionario funcionario = dbFunc.getByMatricula(matricula);
        if (funcionario != null) {
            return new ResponseEntity<>(funcionario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(funcionario, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/funcionario/{matricula}")
    public ResponseEntity<List<String>> editarFunc(@PathVariable UUID matricula, @RequestBody Funcionario funcionario) {
        List<String> erros = funcService.editar(matricula, funcionario);
        if (erros.isEmpty()) {
            return new ResponseEntity<List<String>>(erros, HttpStatus.OK);
        } else if (erros.get(0).equals("Funcionário não encontrado, verifique se a matrícula está correta")) {
            return new ResponseEntity<List<String>>(erros, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<String>>(erros, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @DeleteMapping("/funcionario/{matricula}")
    public ResponseEntity<String> deletarFunc(@PathVariable UUID matricula) {
        Funcionario funcionario = dbFunc.getByMatricula(matricula);
        if (funcionario != null) {
            dbFunc.deleteByMatricula(matricula);
            return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cartao/{idCiclista}")
    public ResponseEntity<Cartao> buscarCartao(@PathVariable UUID idCiclista) {
        Cartao cartao = dbCartao.getByCiclista_Id(idCiclista);
        // cartao.setCiclista(null); //-> Pode ativar a linha caso não queira
        // informações do ciclista aparecendo quando recuperar dados do cartão
        if (cartao != null) {
            return new ResponseEntity<>(cartao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cartao, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cartao/{idCiclista}")
    public ResponseEntity<Cartao> editarCartao(@PathVariable UUID idCiclista, @RequestBody Cartao cartao) {
        List<String> erros = cicService.editarCartao(idCiclista, cartao);
        if (erros.isEmpty()) {
            return new ResponseEntity<>(cartao, HttpStatus.OK);
        } else if (erros.get(0).equals("Não encontrado")) {
            return new ResponseEntity<>(cartao, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cartao, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/aluguel")
    public ResponseEntity<String> alugar(@RequestBody AluguelDTO aluguelDto) {
        String erro = cicService.alugar(aluguelDto);
        if (erro != null) {
            return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(erro, HttpStatus.OK);
        }
    }

    @PostMapping("/devolucao")
    public ResponseEntity<String> devolver(@RequestBody DevolucaoDTO devolucaoDto) {
        String erro = cicService.devolver(devolucaoDto);
        if (erro != null) {
            return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(erro, HttpStatus.OK);
        }
    }
}
