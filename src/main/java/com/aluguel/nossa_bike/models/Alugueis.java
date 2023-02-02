package com.aluguel.nossa_bike.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "alugueis")
public class Alugueis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column
    LocalDateTime dataHora;
    @Column
    UUID tranca;
    @Column
    UUID bicicleta;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id")
    Cartao cartao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciclista_id")
    Ciclista ciclista;


    public Alugueis() {
    }

    public Alugueis(UUID id, LocalDateTime dataHora, UUID tranca, UUID bicicleta, Cartao cartao, Ciclista ciclista) {
        this.id = id;
        this.dataHora = dataHora;
        this.tranca = tranca;
        this.bicicleta = bicicleta;
        this.cartao = cartao;
        this.ciclista = ciclista;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public UUID getTranca() {
        return this.tranca;
    }

    public void setTranca(UUID tranca) {
        this.tranca = tranca;
    }

    public UUID getBicicleta() {
        return this.bicicleta;
    }

    public void setBicicleta(UUID bicicleta) {
        this.bicicleta = bicicleta;
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
