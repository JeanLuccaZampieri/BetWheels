package com.api.projeto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.projeto.model.CarroModel;

@Repository
public interface CarroRepository extends CrudRepository<CarroModel, Long> {

    List<CarroModel> findAll();

    CarroModel findById(long id);
}
