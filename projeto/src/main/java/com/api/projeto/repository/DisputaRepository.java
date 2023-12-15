package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.DisputaModel;

@Repository
public interface DisputaRepository extends CrudRepository<DisputaModel, Long>{
  
   List<DisputaModel> findAll();

    DisputaModel findById(long id);
}
