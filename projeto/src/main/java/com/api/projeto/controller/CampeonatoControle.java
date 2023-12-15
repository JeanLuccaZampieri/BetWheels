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
import org.springframework.web.bind.annotation.RestController;

import com.api.projeto.model.CampeonatoModel;
import com.api.projeto.model.RespostaModel;

import com.api.projeto.service.CampeonatoService;

@RestController
@CrossOrigin(origins = "*")
public class CampeonatoControle {

    @Autowired
    private CampeonatoService cas;

    @DeleteMapping("/campeonato/remover/{id}")
    public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
        return cas.remover(id);
    }

    @PutMapping("/campeonato/alterar")
    public ResponseEntity<?> cadastrarAlterar(@RequestBody CampeonatoModel cam) {
        return cas.cadastrarAlterar(cam, "alterar");
    }

    @PostMapping("/campeonato/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody CampeonatoModel cam) {
        return cas.cadastrarAlterar(cam, "cadastrar");
    }

    @GetMapping("/campeonato/listar")
    public Iterable<CampeonatoModel> listar() {
        return cas.listar();
    }

    @GetMapping("/campeonato/")
    public String rota() {
        return "API de campeonatos funcionando!";
    }

    @GetMapping("/campeonato/{id}")
    public ResponseEntity<?> buscarCampeonatoPorId(@PathVariable Long id) {
        Optional<CampeonatoModel> campeonatoOptional = cas.buscarPorId(id);

        if (campeonatoOptional.isPresent()) {
            return new ResponseEntity<>(campeonatoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("campeonato não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
