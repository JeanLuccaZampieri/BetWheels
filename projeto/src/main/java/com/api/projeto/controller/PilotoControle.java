package com.api.projeto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.projeto.model.PilotoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.EquipeModel;
import com.api.projeto.model.CarroModel;
import com.api.projeto.service.CarroService;
import com.api.projeto.service.EquipeService;
import com.api.projeto.service.PilotoService;

@RestController
@CrossOrigin(origins = "*")
public class PilotoControle {

  @Autowired
  private PilotoService ps;

  @DeleteMapping("/piloto/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return ps.remover(id);
  }

  @PutMapping("/piloto/alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody PilotoModel pm) {
    return ps.cadastrarAlterar(pm, "alterar");
  }

  @Autowired
  private EquipeService equipeService;
  @Autowired
  private CarroService carroService;
  
  @PostMapping("/piloto/cadastrar-alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody PilotoModel tm,
                                            @RequestParam String acao,
                                            @RequestParam Long equipeId,
                                            @RequestParam Long carroId) {
      RespostaModel rm = new RespostaModel();
  
      try {
          if (tm.getNome().equals("")) {
              rm.setMensagem("O nome é obrigatório!");
              return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
          } else {
              // Verifica se o ID da equipe associada está presente
              if (equipeId == null) {
                  rm.setMensagem("ID da equipe associada é obrigatório!");
                  return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
              }if (carroId == null) {
                  rm.setMensagem("ID do carro associada é obrigatório!");
                  return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
              }
  
              // Recupera a equipe completa do banco de dados pelo ID
              Optional<EquipeModel> equipeOptional = equipeService.buscarPorId(equipeId);
              if (equipeOptional.isEmpty()) {
                  rm.setMensagem("Equipe associada não encontrada!");
                  return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
              }
  
              EquipeModel equipePersistida = equipeOptional.get();
              tm.setEquipe(equipePersistida);
              
               Optional<CarroModel> carroOptional = carroService.buscarPorId(carroId);
              if (carroOptional.isEmpty()) {
                  rm.setMensagem("Carro associada não encontrada!");
                  return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
              }
  
              CarroModel carroPersistido = carroOptional.get();
              tm.setCarro(carroPersistido);
  
              // Decide se é uma operação de cadastrar ou alterar
              if ("cadastrar".equals(acao)) {
                  return ps.cadastrarAlterar(tm, "cadastrar");
              } else {
                  return ps.cadastrarAlterar(tm, "alterar");
              }
          }
      } catch (Exception e) {
          e.printStackTrace(); // Isso imprimirá o stack trace no console
          return new ResponseEntity<>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  @GetMapping("/piloto/listar")
  public Iterable<PilotoModel> listar() {
    return ps.listar();
  }

  @GetMapping("/piloto/")
  public String rota() {
    return "Api de produtos funcionando!";
  }
  @GetMapping("/piloto/{id}")
  public ResponseEntity<?> buscarPilotoPorId(@PathVariable Long id) {
      Optional<PilotoModel> pilotoOptional = ps.buscarPorId(id);

      if (pilotoOptional.isPresent()) {
          return new ResponseEntity<>(pilotoOptional.get(), HttpStatus.OK);
      } else {
          return new ResponseEntity<>("Piloto não encontrado", HttpStatus.NOT_FOUND);
      }
  }
}
