package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class BicicletaDTO {

    public enum Status {
        DISPONIVEL("disponivel"),
        EM_USO("em uso"),
        NOVA("nova"), 
        APOSENTADA("aposentada"),
        EM_REPARO("em reparo"),
        REPARO_SOLICITADO("reparo solicitado");
    
        private final String descricao;
    
        Status(String descricao){
            this.descricao = descricao;
        }
    
        public String getDescricao(){
            return descricao;
        }
    }

    UUID uuid;
    String marca;
    String modelo;
    String ano;
    int numero;
    Status status;


    public BicicletaDTO() {
    }

    public BicicletaDTO(UUID uuid, String marca, String modelo, String ano, int numero, Status status) {
        this.uuid = uuid;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
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

    public Status getStatus() {
        return this.status;
    }

    public void setStatusBic(Status status) {
        this.status = status;
    }
    
}
