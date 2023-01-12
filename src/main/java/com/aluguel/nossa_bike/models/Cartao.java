package com.aluguel.nossa_bike.models;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column
    String nomeTitular;
    @Column
    long numero;
    @Column
    String validade;
    @Column
    int cvv;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciclista_ID")
    Ciclista ciclista;

    public Cartao() {
    }

    public Cartao(UUID id, String nomeTitular, long numero, String validade, int cvv, Ciclista ciclista) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
        this.ciclista = ciclista;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeTitular() {
        return this.nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public Long getNumero() {
        return this.numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return this.validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public int getCvv() {
        return this.cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Ciclista getCiclista() {
        return this.ciclista;
    }

    public void setCiclista(Ciclista ciclista) {
        this.ciclista = ciclista;
    }

}
