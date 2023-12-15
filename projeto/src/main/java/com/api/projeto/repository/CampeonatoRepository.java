package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.CampeonatoModel;

@Repository
public interface CampeonatoRepository extends CrudRepository<CampeonatoModel, Long> {
  
    List<CampeonatoModel> findAll();

    CampeonatoModel findById(long id);
}
