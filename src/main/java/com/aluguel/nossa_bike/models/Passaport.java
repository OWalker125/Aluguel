package com.aluguel.nossa_bike.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Passaport {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;
    @Column
    int ciclistaId;
    @Column
    String validade;
    @Column
    String pais;


    public Passaport() {
    }

    public Passaport(int id, int ciclistaId, String validade, String pais) {
        this.id = id;
        this.ciclistaId = ciclistaId;
        this.validade = validade;
        this.pais = pais;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCiclistaId(int ciclistaId) {
        this.ciclistaId = ciclistaId;
    }

    public int getCiclistaId() {
        return this.ciclistaId;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getValidade() {
        return this.validade;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return this.pais;
    }
}
