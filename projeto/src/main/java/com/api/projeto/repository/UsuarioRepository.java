package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.api.projeto.model.UsuarioModel;



@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    
  List<UsuarioModel> findAll();

    UsuarioModel findById(long id);
}
