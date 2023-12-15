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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.TransacaoModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.service.TransacaoService;
import com.api.projeto.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class TransacaoControle {

  @Autowired
  private TransacaoService ts;

  @DeleteMapping("/transacao/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return ts.remover(id);
  }
  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/transacao/cadastrar-alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody TransacaoModel tm, @RequestParam String acao,
      @RequestParam Long usuarioId) {
    RespostaModel rm = new RespostaModel();

    try {
      if (tm.getValor().equals("")) {
        rm.setMensagem("O extrato é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      } else {
        // Verifica se o ID do usuário associado está presente
        if (usuarioId == null) {
          rm.setMensagem("ID do usuário associado é obrigatório!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        // Recupera o usuário completo do banco de dados pelo ID
        Optional<UsuarioModel> usuarioOptional = usuarioService.buscarPorId(usuarioId);
        if (usuarioOptional.isEmpty()) {
          rm.setMensagem("Usuário associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        UsuarioModel usuarioPersistido = usuarioOptional.get();
        tm.setUsuario(usuarioPersistido);

        // Decide se é uma operação de cadastrar ou alterar
        if (acao.equals("cadastrar")) {
          return ts.cadastrarAlterar(tm, "cadastrar");
        } else {
          return ts.cadastrarAlterar(tm, "alterar");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
      
    }
  }

  @GetMapping("/transacao/listar")
  public Iterable<TransacaoModel> listar() {
    return ts.listar();
  }

  @GetMapping("/transacao/")
  public String rota() {
    return "Api de produtos funcionando!";
  }

  @GetMapping("/transacao/{id}")
  public ResponseEntity<TransacaoModel> buscarPorId(@PathVariable Long id) {
    Optional<TransacaoModel> transacao = ts.buscarPorId(id);

    return transacao
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
