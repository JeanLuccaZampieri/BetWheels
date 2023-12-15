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

import com.api.projeto.model.EquipeModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.service.EquipeService;


@RestController
@CrossOrigin(origins = "*")
public class EquipeControle {


  @Autowired
  private EquipeService es;

  @DeleteMapping("/equipe/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return es.remover(id);
  }

  @PutMapping("/equipe/alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody EquipeModel em) {
    return es.cadastrarAlterar(em, "alterar");
  }

  @PostMapping("/equipe/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody EquipeModel em) {
    return es.cadastrarAlterar(em, "cadastrar");
  }

  @GetMapping("/equipe/listar")
  public Iterable<EquipeModel> listar() {
    return es.listar();
  }

  @GetMapping("/equipe/")
  public String rota() {
    return "Api de produtos funcionando!";
  }
 
  @GetMapping("/equipe/{id}")
  public ResponseEntity<EquipeModel> buscarPorId(@PathVariable Long id) {
    Optional<EquipeModel> equipe = es.buscarPorId(id);

    return equipe
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
