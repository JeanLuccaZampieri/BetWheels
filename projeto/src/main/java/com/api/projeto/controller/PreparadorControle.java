package com.api.projeto.controller;

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

import com.api.projeto.model.PreparadorModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.service.PreparadorService;


@RestController
@CrossOrigin(origins = "*")
public class PreparadorControle {


  @Autowired
  private PreparadorService pes;

  @DeleteMapping("/preparador/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return pes.remover(id);
  }

  @PutMapping("/preparador/alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody PreparadorModel pem) {
    return pes.cadastrarAlterar(pem, "alterar");
  }

  @PostMapping("/preparador/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody PreparadorModel pem) {
    return pes.cadastrarAlterar(pem, "cadastrar");
  }

  @GetMapping("/preparador/listar")
  public Iterable<PreparadorModel> listar() {
    return pes.listar();
  }

  @GetMapping("/preparador/")
  public String rota() {
    return "Api de produtos funcionando!";
  }
}
