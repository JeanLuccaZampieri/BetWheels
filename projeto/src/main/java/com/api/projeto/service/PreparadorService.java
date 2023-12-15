package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.api.projeto.model.PreparadorModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.PreparadorRepository;

@Service
public class PreparadorService {
 
   @Autowired
  private RespostaModel rm;

  @Autowired
  private PreparadorRepository per;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(PreparadorModel pem, String acao) {

    if (pem.getNome().equals("")) {
      rm.setMensagem("O nome do preparador é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<PreparadorModel>(per.save(pem), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<PreparadorModel>(per.save(pem), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    per.deleteById(id);
    rm.setMensagem("O preparador foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<PreparadorModel> listar() {
    return per.findAll();
  }
   public Optional<PreparadorModel> buscarPorId(Long id) {
    return per.findById(id);
  }
}
