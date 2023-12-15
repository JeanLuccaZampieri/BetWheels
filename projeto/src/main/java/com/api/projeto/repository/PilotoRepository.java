package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.PilotoModel;

@Repository
public interface PilotoRepository extends CrudRepository<PilotoModel, Long>{
 
   List<PilotoModel> findAll();
   List<PilotoModel> findAllByIdIn(List<Long> ids);
   PilotoModel findById(long id);
}
