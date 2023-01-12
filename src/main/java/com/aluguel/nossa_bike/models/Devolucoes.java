package com.aluguel.nossa_bike.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table
public class Devolucoes{

    public enum StatusFatura{ABERTA,PAGA}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column
    LocalDateTime devolucao;
    @Column
    LocalDateTime cobranca;
    @Column
    long extras;
    @Column
    StatusFatura statusfatura;
    @Column
    UUID bicicleta;
    @Column
    UUID tranca;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id")
    Cartao cartao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciclista_id")
    @Column
    Ciclista ciclista;

    public Devolucoes() {
    }

    public Devolucoes(UUID id, LocalDateTime devolucao, LocalDateTime cobranca, long extras, StatusFatura statusfatura, UUID bicicleta, UUID tranca, Cartao cartao, Ciclista ciclista) {
        this.id = id;
        this.devolucao = devolucao;
        this.cobranca = cobranca;
        this.extras = extras;
        this.statusfatura = statusfatura;
        this.bicicleta = bicicleta;
        this.tranca = tranca;
        this.cartao = cartao;
        this.ciclista = ciclista;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDevolucao() {
        return this.devolucao;
    }

    public void setDevolucao(LocalDateTime devolucao) {
        this.devolucao = devolucao;
    }

    public LocalDateTime getCobranca() {
        return this.cobranca;
    }

    public void setCobranca(LocalDateTime cobranca) {
        this.cobranca = cobranca;
    }

    public long getExtras() {
        return this.extras;
    }

    public void setExtras(long extras) {
        this.extras = extras;
    }

    public StatusFatura getStatusfatura() {
        return this.statusfatura;
    }

    public void setStatusfatura(StatusFatura statusfatura) {
        this.statusfatura = statusfatura;
    }

    public UUID getBicicleta() {
        return this.bicicleta;
    }

    public void setBicicleta(UUID bicicleta) {
        this.bicicleta = bicicleta;
    }

    public UUID getTranca() {
        return this.tranca;
    }

    public void setTranca(UUID tranca) {
        this.tranca = tranca;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Ciclista getCiclista() {
        return this.ciclista;
    }

    public void setCiclista(Ciclista ciclista) {
        this.ciclista = ciclista;
    }

}
