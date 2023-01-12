package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class NovaCobrancaDTO {
    private double valor;
    private UUID ciclista;

    public NovaCobrancaDTO() {
    }

    public NovaCobrancaDTO(double valor, UUID ciclista) {
        this.valor = valor;
        this.ciclista = ciclista;
    }
  
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public UUID getCiclista() {
        return ciclista;
    }
    public void setCiclista(UUID ciclista) {
        this.ciclista = ciclista;
    }
}
