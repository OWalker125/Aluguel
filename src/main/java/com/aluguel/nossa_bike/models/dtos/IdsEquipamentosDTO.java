package com.aluguel.nossa_bike.models.dtos;

import java.util.UUID;

public class IdsEquipamentosDTO {
    private UUID totem;

    private UUID tranca;

    private UUID bicicleta;

    public IdsEquipamentosDTO() {
    }

    public IdsEquipamentosDTO(UUID totem, UUID tranca, UUID bicicleta) {
        this.totem = totem;
        this.tranca = tranca;
        this.bicicleta = bicicleta;
    }

    public UUID getTotem() {
        return this.totem;
    }

    public void setTotem(UUID totem) {
        this.totem = totem;
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
    
}
