package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.PilotoModel;
import com.api.projeto.model.RespostaModel;

import com.api.projeto.repository.PilotoRepository;

@Service
public class PilotoService {
  
   @Autowired
  private RespostaModel rm;

  @Autowired
  private PilotoRepository pr;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(PilotoModel pm, String acao) {

    if (pm.getNome().equals("")) {
      rm.setMensagem("O nome do piloto é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<PilotoModel>(pr.save(pm), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<PilotoModel>(pr.save(pm), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    pr.deleteById(id);
    rm.setMensagem("O piloto foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<PilotoModel> listar() {
    return pr.findAll();
  }
   public Optional<PilotoModel> buscarPorId(Long id) {
    return pr.findById(id);
  }
}
