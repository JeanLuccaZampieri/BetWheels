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

import com.api.projeto.model.ApostaModel;
import com.api.projeto.model.DisputaModel;
import com.api.projeto.model.PilotoModel;
import com.api.projeto.model.ApostaModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.service.ApostaService;
import com.api.projeto.service.DisputaService;
import com.api.projeto.service.PilotoService;
import com.api.projeto.service.PreparadorService;
import com.api.projeto.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class ApostaControle {

  @Autowired
  private ApostaService as;

  @DeleteMapping("/aposta/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return as.remover(id);
  }

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private PilotoService pilotoService;

    @Autowired
  private DisputaService disputaService;

  @PostMapping("/aposta/cadastrar-alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody ApostaModel am, @RequestParam String acao,
      @RequestParam Long usuarioId, @RequestParam Long pilotoId, @RequestParam Long disputaId) {
    RespostaModel rm = new RespostaModel();

    try {
      if (am.getValor() == null) {
        rm.setMensagem("O nome é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      } else {
        Optional<UsuarioModel> usuarioOptional = usuarioService.buscarPorId(usuarioId);
        if (usuarioOptional.isEmpty()) {
          rm.setMensagem("Usuário associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        UsuarioModel usuarioPersistido = usuarioOptional.get();
        am.setUsuario(usuarioPersistido);

         Optional<PilotoModel> pilotoOptional = pilotoService.buscarPorId(pilotoId);
        if (pilotoOptional.isEmpty()) {
          rm.setMensagem("Piloto associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        PilotoModel pilotoPersistido = pilotoOptional.get();
        am.setPiloto(pilotoPersistido);
        Float valorDescontado = am.getValor();
        Float saldoUser = usuarioPersistido.getSaldo();
        Float saldoAtualUser = saldoUser - valorDescontado;
        usuarioPersistido.setSaldo(saldoAtualUser);

        Optional<DisputaModel> disputaOptional = disputaService.buscarPorId(disputaId);
        if (disputaOptional.isEmpty()) {
          rm.setMensagem("Disputa associada não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        DisputaModel disputaPersistido = disputaOptional.get();
        am.setDisputa(disputaPersistido);


        // Decide se é uma operação de cadastrar ou alterar
        if (acao.equals("cadastrar")) {
          return as.cadastrarAlterar(am, "cadastrar");
        } else {
          return as.cadastrarAlterar(am, "alterar");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @GetMapping("/aposta/listar")
  public Iterable<ApostaModel> listar() {
    return as.listar();
  }

  @GetMapping("/aposta/")
  public String rota() {
    return "Api de produtos funcionando!";
  }
}
