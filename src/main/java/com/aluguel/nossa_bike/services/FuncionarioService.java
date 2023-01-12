package com.aluguel.nossa_bike.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aluguel.nossa_bike.models.Funcionario;
import com.aluguel.nossa_bike.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository dbFunc;

    @Autowired
    ValidationService validator;

    public List<String> cadastrar(Funcionario funcionario) {
        List<String> erros = validator.isValid(funcionario);
        if (erros.isEmpty()) {
            dbFunc.save(funcionario);
            return erros;
        } else {
            return erros;
        }
    }

    public List<String> editar(UUID matricula, Funcionario funcionario) {
        List<String> erros = new LinkedList<>();
        Funcionario funcSalvo = dbFunc.getByMatricula(matricula);
        if (funcSalvo != null) {
            erros = validator.isValid(funcionario);
            if (erros.isEmpty()) {
                funcionario.setMatricula(matricula);
                dbFunc.save(funcionario);
            }
            return erros;
        }
        erros.add("Funcionário não encontrado, verifique se a matrícula está correta");
        return erros;
    }
}
