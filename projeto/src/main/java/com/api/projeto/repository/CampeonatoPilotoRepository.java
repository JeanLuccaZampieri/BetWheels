package com.api.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.CampeonatoPilotoModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampeonatoPilotoRepository extends JpaRepository<CampeonatoPilotoModel, Long> {
    List<CampeonatoPilotoModel> findByCampeonatoIdAndAtivoTrue(Long campeonatoId);
    Optional<CampeonatoPilotoModel> findByPilotoIdAndCampeonatoId(Long pilotoId, Long campeonatoId);
}