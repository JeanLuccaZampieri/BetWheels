package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.PreparadorModel;

@Repository
public interface PreparadorRepository extends CrudRepository<PreparadorModel, Long>{
  
   List<PreparadorModel> findAll();

    PreparadorModel findById(long id);
}
