package com.api.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.ApostaModel;

import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.ApostaRepository;

@Service
public class ApostaService {
  @Autowired
  private RespostaModel rm;

  @Autowired
  private ApostaRepository ar;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(ApostaModel am, String acao) {

    if (am.getValor().equals("")) {
      rm.setMensagem("O valor da aposta é obrigatória!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<ApostaModel>(ar.save(am), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<ApostaModel>(ar.save(am), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    ar.deleteById(id);
    rm.setMensagem("A aposta foi removida com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<ApostaModel> listar() {
    return ar.findAll();
  }

}
