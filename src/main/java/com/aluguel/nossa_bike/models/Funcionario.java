package com.aluguel.nossa_bike.models;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID matricula;
    @Column
    String senha;
    @Column
    String emailUser;
    @Column
    String nome;
    @Column
    int idade;
    @Column
    String funcao;
    @Column
    String cpf;

    public Funcionario() {
    }

    public Funcionario(UUID matricula, String senha, String emailUser, String nome, int idade, String funcao, String cpf) {
        this.matricula = matricula;
        this.senha = senha;
        this.emailUser = emailUser;
        this.nome = nome;
        this.idade = idade;
        this.funcao = funcao;
        this.cpf = cpf;
    }

    public UUID getMatricula() {
        return this.matricula;
    }

    public void setMatricula(UUID matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailUser() {
        return this.emailUser;
    }

    public void setEmailUser(String email) {
        this.emailUser = email;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
