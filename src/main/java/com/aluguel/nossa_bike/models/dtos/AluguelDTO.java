package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class AluguelDTO {
    UUID ciclista;
    UUID trancaInicio;


    public UUID getCiclista() {
        return this.ciclista;
    }

    public void setCiclista(UUID ciclista) {
        this.ciclista = ciclista;
    }

    public UUID getTrancaInicio() {
        return this.trancaInicio;
    }

    public void setTrancaInicio(UUID trancaInicio) {
        this.trancaInicio = trancaInicio;
    }
}
