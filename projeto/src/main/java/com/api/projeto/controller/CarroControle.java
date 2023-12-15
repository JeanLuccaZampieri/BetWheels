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

import com.api.projeto.model.CarroModel;
import com.api.projeto.model.PreparadorModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.service.CarroService;
import com.api.projeto.service.PreparadorService;

@RestController
@CrossOrigin(origins = "*")
public class CarroControle {

  @Autowired
  private CarroService cs;

  @DeleteMapping("/carro/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return cs.remover(id);
  }

  @PutMapping("/carro/alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody CarroModel cm) {
    return cs.cadastrarAlterar(cm, "alterar");
  }

  @Autowired
  private PreparadorService preparadorService;
  @PostMapping("/carro/cadastrar-alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody CarroModel cm, @RequestParam String acao,
      @RequestParam Long preparadorId) {
    RespostaModel rm = new RespostaModel();

    try {
      if (cm.getNome().equals("")) {
        rm.setMensagem("O nome é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      } else {
        // Verifica se o ID do usuário associado está presente
        if (preparadorId == null) {
          rm.setMensagem("ID do usuário associado é obrigatório!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        // Recupera o usuário completo do banco de dados pelo ID
        Optional<PreparadorModel> preparadorOptional = preparadorService.buscarPorId(preparadorId);
        if (preparadorOptional.isEmpty()) {
          rm.setMensagem("Usuário associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        PreparadorModel preparadorPersistido = preparadorOptional.get();
        cm.setPreparador(preparadorPersistido);

        // Decide se é uma operação de cadastrar ou alterar
        if (acao.equals("cadastrar")) {
          return cs.cadastrarAlterar(cm, "cadastrar");
        } else {
          return cs.cadastrarAlterar(cm, "alterar");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
      
    }
  }

  @GetMapping("/carro/listar")
  public Iterable<CarroModel> listar() {
    return cs.listar();
  }

  @GetMapping("/carro/")
  public String rota() {
    return "Api de produtos funcionando!";
  }
  /*
   * @Autowired
   * private CarRepository acao;
   * 
   * @PostMapping("/projeto")
   * public CarEntity cadastrar(@RequestBody CarEntity obj) {
   * return acao.save(obj);
   * }
   * 
   * @PostMapping("/car")
   * public CarEntity carEntity(@RequestBody CarEntity c) {
   * return c;
   * 
   * }
   */
}
