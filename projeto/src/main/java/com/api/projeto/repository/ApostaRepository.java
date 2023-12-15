package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.ApostaModel;
import com.api.projeto.model.DisputaModel;

@Repository
public interface ApostaRepository extends CrudRepository<ApostaModel, Long>{
  
   List<ApostaModel> findAll();

    ApostaModel findById(long id);
}
