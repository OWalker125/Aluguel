package com.aluguel.nossa_bike.models;

public class Passaport{
    int numero;
    String validade;
    String pais;

    public Passaport(int numero, String validade, String pais){
        this.numero = numero;
        this.validade = validade;
        this.pais = pais;
    }

    public int getNumero(){return this.numero;}

    public String getValidade(){return this.validade;}

    public String getPais(){return this.pais;}
}
