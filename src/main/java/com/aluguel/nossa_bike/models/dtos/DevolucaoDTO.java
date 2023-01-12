package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class DevolucaoDTO {
    UUID idTranca;
    UUID idBicicleta;

    public DevolucaoDTO() {
    }

    public DevolucaoDTO(UUID idTranca, UUID idBicicleta) {
        this.idTranca = idTranca;
        this.idBicicleta = idBicicleta;
    }

    public UUID getIdTranca() {
        return this.idTranca;
    }

    public void setIdTranca(UUID idTranca) {
        this.idTranca = idTranca;
    }

    public UUID getIdBicicleta() {
        return this.idBicicleta;
    }

    public void setIdBicicleta(UUID idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

}
