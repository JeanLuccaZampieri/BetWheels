package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.TransacaoModel;

@Repository
public interface TransacaoRepository extends CrudRepository<TransacaoModel, Long>{

  List<TransacaoModel> findAll();
  
  TransacaoModel findById(long id);
}
