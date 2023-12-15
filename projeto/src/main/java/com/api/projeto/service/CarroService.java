package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.CarroModel;
import com.api.projeto.repository.CarroRepository;

@Service
public class CarroService {

  @Autowired
  private RespostaModel rm;

  @Autowired
  private CarroRepository cr;

  // Matodo para cadastrar e alterar carro
 public ResponseEntity<?> cadastrarAlterar(CarroModel cm, String acao) {

    if (cm.getNome().equals("")) {
      rm.setMensagem("O nome do piloto é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<CarroModel>(cr.save(cm), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<CarroModel>(cr.save(cm), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    cr.deleteById(id);
    rm.setMensagem("O carro foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<CarroModel> listar() {
    return cr.findAll();
  }
   public Optional<CarroModel> buscarPorId(Long id) {
    return cr.findById(id);
  }

}
