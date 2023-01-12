package com.aluguel.nossa_bike.models;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Ciclista {

    public enum AccountStatus {
        ATIVO, INATIVO, AGUARDANDO_CONFIRMACAO
    }

    public enum Nacionalidade {
        BRASILEIRO, ESTRANGEIRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String nome;
    @Column
    private AccountStatus status;
    @Column
    private String nascimento;
    @Column
    private String cpf;
    @Column
    private Nacionalidade nacionalidade;
    @Column
    private String emailUser;
    @Column
    private String urlFotoDocumento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passaport_id")
    private Passaport passaport;

    public Ciclista() {
    }

    public Ciclista(UUID id, String nome, AccountStatus status, String nascimento, String cpf, Passaport passaport,
            Nacionalidade nacionalidade, String emailUser, String urlFotoDocumento) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.passaport = passaport;
        this.nacionalidade = nacionalidade;
        this.emailUser = emailUser;
        this.urlFotoDocumento = urlFotoDocumento;
    }


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getNascimento() {
        return this.nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Nacionalidade getNacionalidade() {
        return this.nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEmailUser() {
        return this.emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getUrlFotoDocumento() {
        return this.urlFotoDocumento;
    }

    public void setUrlFotoDocumento(String urlFotoDocumento) {
        this.urlFotoDocumento = urlFotoDocumento;
    }

    public Passaport getPassaport() {
        return this.passaport;
    }

    public void setPassaport(Passaport passaport) {
        this.passaport = passaport;
    }
   
}