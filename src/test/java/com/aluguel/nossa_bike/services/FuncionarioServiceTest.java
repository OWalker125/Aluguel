package com.aluguel.nossa_bike.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.aluguel.nossa_bike.models.Cartao;
import com.aluguel.nossa_bike.models.Funcionario;
import com.aluguel.nossa_bike.repository.FuncionarioRepository;

public class FuncionarioServiceTest {
    @Mock
    FuncionarioRepository dbFunc;

    @Mock
    ValidationService validator;

    @InjectMocks
    FuncionarioService fService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenIsValidThenBlankList(){
        List<String> vazia = new LinkedList<String>();
        Funcionario funcionario = new Funcionario(null, "123", null, null, 0, null, null);
        when(validator.isValidFunc(funcionario)).thenReturn(vazia);
        when(dbFunc.save(any())).thenReturn(null);
        when(dbFunc.getByMatricula(any())).thenReturn(funcionario);

        assertEquals(vazia, fService.cadastrar(any()));
        assertEquals(vazia, fService.editar(UUID.randomUUID(), funcionario));
    }

    @Test
    public void whenIsInvalidThenErros(){
        List<String> erro = new LinkedList<String>();
        erro.add("erro");
        Funcionario funcionario = new Funcionario(null, "123", null, null, 0, null, null);
        when(validator.isValidFunc(any())).thenReturn(erro);
        when(dbFunc.save(any())).thenReturn(null);

        assertEquals(erro, fService.cadastrar(any()));
        assertEquals(erro, fService.editar(UUID.randomUUID(), funcionario));
    }
}
