package com.aluguel.nossa_bike.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aluguel.nossa_bike.models.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    Cartao getByCiclista_Id(UUID ciclista_ID);

}
