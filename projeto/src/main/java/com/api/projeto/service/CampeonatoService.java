package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.CampeonatoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.CampeonatoRepository;




@Service
public class CampeonatoService {

   @Autowired
  private RespostaModel rm;

  @Autowired
  private CampeonatoRepository car;

  @Autowired
  public CampeonatoService(RespostaModel rm, CampeonatoRepository car) {
      this.rm = rm;
      this.car = car;
  }
  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(CampeonatoModel cam, String acao) {

    if (cam.getNome().equals("")) {
      rm.setMensagem("O nome do campeonato é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<CampeonatoModel>(car.save(cam), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<CampeonatoModel>(car.save(cam), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    car.deleteById(id);
    rm.setMensagem("O campeonato foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<CampeonatoModel> listar() {
    return car.findAll();
  }
   public Optional<CampeonatoModel> buscarPorId(Long id) {
    return car.findById(id);
  }
}
