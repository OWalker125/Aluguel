package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class BicicletaDTO {

    public enum StatusBic{DISPONÍVEL,EM_USO,NOVA,APOSENTADA,REPARO_SOLICITADO,EM_REPARO}

    UUID id;
    String marca;
    String modelo;
    String ano;
    int numero;
    StatusBic statusBic ;


    public BicicletaDTO() {
    }

    public BicicletaDTO(UUID id, String marca, String modelo, String ano, int numero, StatusBic statusBic) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.statusBic = statusBic;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return this.ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public StatusBic getStatusBic() {
        return this.statusBic;
    }

    public void setStatusBic(StatusBic statusBic) {
        this.statusBic = statusBic;
    }
    
}
