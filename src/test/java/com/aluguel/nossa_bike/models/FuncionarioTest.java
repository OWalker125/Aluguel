package com.aluguel.nossa_bike.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class FuncionarioTest {
    
    @Test
    public void testGettersAndSetters() {
        UUID matricula = UUID.randomUUID();
        String senha = "mypassword";
        String emailUser = "user@example.com";
        String nome = "John Doe";
        int idade = 30;
        String funcao = "Manager";
        String cpf = "123.456.789-00";

        Funcionario funcionario = new Funcionario(matricula, senha, emailUser, nome, idade, funcao, cpf);

        assertEquals(matricula, funcionario.getMatricula());
        assertEquals(senha, funcionario.getSenha());
        assertEquals(emailUser, funcionario.getEmailUser());
        assertEquals(nome, funcionario.getNome());
        assertEquals(idade, funcionario.getIdade());
        assertEquals(funcao, funcionario.getFuncao());
        assertEquals(cpf, funcionario.getCpf());
    }

    @Test
    public void testSetters() {
        Funcionario funcionario = new Funcionario();
        UUID matricula = UUID.randomUUID();
        String senha = "mypassword";
        String emailUser = "user@example.com";
        String nome = "John Doe";
        int idade = 30;
        String funcao = "Manager";
        String cpf = "123.456.789-00";

        funcionario.setMatricula(matricula);
        funcionario.setSenha(senha);
        funcionario.setEmailUser(emailUser);
        funcionario.setNome(nome);
        funcionario.setIdade(idade);
        funcionario.setFuncao(funcao);
        funcionario.setCpf(cpf);

        assertEquals(matricula, funcionario.getMatricula());
        assertEquals(senha, funcionario.getSenha());
        assertEquals(emailUser, funcionario.getEmailUser());
        assertEquals(nome, funcionario.getNome());
        assertEquals(idade, funcionario.getIdade());
        assertEquals(funcao, funcionario.getFuncao());
        assertEquals(cpf, funcionario.getCpf());
    }

}
