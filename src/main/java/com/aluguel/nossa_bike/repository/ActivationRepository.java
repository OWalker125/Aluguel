package com.aluguel.nossa_bike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluguel.nossa_bike.models.ActivationLog;

public interface ActivationRepository extends JpaRepository<ActivationLog, Integer>{
    
}
