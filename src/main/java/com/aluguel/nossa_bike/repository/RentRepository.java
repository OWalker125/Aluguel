package com.aluguel.nossa_bike.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluguel.nossa_bike.models.*;

public interface RentRepository extends JpaRepository<AluguelStatus, Integer>{

    AluguelStatus getByCiclista_Id(UUID ciclista_ID);
}
