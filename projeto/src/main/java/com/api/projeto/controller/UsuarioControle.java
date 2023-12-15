package com.api.projeto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioControle {

  @Autowired
  private UsuarioService us;

  @DeleteMapping("/usuario/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return us.remover(id);
  }

  @PutMapping("/usuario/alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody UsuarioModel um) {
    return us.cadastrarAlterar(um, "alterar");
  }

  @PostMapping("/usuario/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody UsuarioModel um) {
    return us.cadastrarAlterar(um, "cadastrar");
  }

  @GetMapping("/usuario/listar")
  public Iterable<UsuarioModel> listar() {
    return us.listar();
  }

  @GetMapping("/usuario/")
  public String rota() {
    return "Api de produtos funcionando!";
  }

   @GetMapping("/usuario/{id}")
  public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable Long id) {
    Optional<UsuarioModel> usuario = us.buscarPorId(id);

    return usuario
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
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
