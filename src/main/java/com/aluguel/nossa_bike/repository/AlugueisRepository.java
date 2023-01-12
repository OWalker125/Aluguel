package com.aluguel.nossa_bike.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.aluguel.nossa_bike.models.Alugueis;

public interface AlugueisRepository extends JpaRepository<Alugueis, Integer>{
    @Query("SELECT * FROM Alugueis WHERE bicicleta = ?1 AND dataHora = (select max(dataHora) FROM Alugueis)")
    Alugueis getNewestById(UUID idBicicleta);
}
