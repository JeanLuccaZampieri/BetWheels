package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.api.projeto.model.EquipeModel;

@Repository
public interface EquipeRepository extends CrudRepository<EquipeModel, Long> {

    List<EquipeModel> findAll();

    EquipeModel findById(long id);
}
