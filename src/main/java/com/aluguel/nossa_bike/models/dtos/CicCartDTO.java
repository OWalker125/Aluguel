package com.aluguel.nossa_bike.models.dtos;

import com.aluguel.nossa_bike.models.Cartao;
import com.aluguel.nossa_bike.models.Ciclista;

public class CicCartDTO {
   Ciclista ciclista;
   Cartao cartao;


    public Ciclista getCiclista() {
        return this.ciclista;
    }

    public void setCiclista(Ciclista ciclista) {
        this.ciclista = ciclista;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
   
}
