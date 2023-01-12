package com.aluguel.nossa_bike.models;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table
public class AluguelStatus {

    public enum Status{LIVRE, ALUGADO}

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    UUID id;
    @Column
    Status rentStatus;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ciclista_ID")
    Ciclista ciclista;

    public AluguelStatus() {
    }

    public AluguelStatus(Ciclista ciclistaID) {
        this.rentStatus = Status.LIVRE;
        this.ciclista = ciclistaID;
    }

    public AluguelStatus(UUID id, Status rentStatus, Ciclista ciclista) {
        this.id = id;
        this.rentStatus = rentStatus;
        this.ciclista = ciclista;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getRentStatus() {
        return this.rentStatus;
    }

    public void setRentStatus(Status rentStatus) {
        this.rentStatus = rentStatus;
    }

    public Ciclista getCiclista() {
        return this.ciclista;
    }

    public void setCiclista(Ciclista ciclista) {
        this.ciclista = ciclista;
    }
    
}