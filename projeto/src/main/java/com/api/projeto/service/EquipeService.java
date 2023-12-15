package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.EquipeModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.EquipeRepository;

@Service
public class EquipeService {

  @Autowired
  private RespostaModel rm;

  @Autowired
  private EquipeRepository er;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(EquipeModel em, String acao) {

    if (em.getNome().equals("")) {
      rm.setMensagem("o nome da equipe é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<EquipeModel>(er.save(em), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<EquipeModel>(er.save(em), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    er.deleteById(id);
    rm.setMensagem("A equipe foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<EquipeModel> listar() {
    return er.findAll();
  }
   public Optional<EquipeModel> buscarPorId(Long id) {
    return er.findById(id);
  }
}
