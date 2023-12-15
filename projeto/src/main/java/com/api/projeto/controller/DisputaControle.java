package com.api.projeto.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.api.projeto.model.ApostaModel;
import com.api.projeto.model.CampeonatoModel;
import com.api.projeto.model.DisputaModel;
import com.api.projeto.model.PilotoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.repository.DisputaRepository;
import com.api.projeto.repository.UsuarioRepository;
import com.api.projeto.service.ApostaService;
import com.api.projeto.service.CampeonatoService;
import com.api.projeto.service.DisputaService;
import com.api.projeto.service.PilotoService;
import com.api.projeto.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class DisputaControle {

  @Autowired
  private DisputaService ds;

  @DeleteMapping("/disputa/remover/{id}")
  public ResponseEntity<RespostaModel> remover(@PathVariable long id) {
    return ds.remover(id);
  }

  @Autowired
  private PilotoService pilotoService;

  @Autowired
  private CampeonatoService campeonatoService;

  @PostMapping("/disputa/cadastrar-alterar")
  public ResponseEntity<?> cadastrarAlterar(@RequestBody DisputaModel dm, @RequestParam String acao,
      @RequestParam Long piloto1Id, @RequestParam Long piloto2Id, @RequestParam Long campeonatoId) {
    RespostaModel rm = new RespostaModel();

    try {
      if (piloto1Id == null) {
        rm.setMensagem("ID do piloto associado é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      } else {
        // Verifica se o ID do usuário associado está presente
        if (piloto2Id == null) {
          rm.setMensagem("ID do piloto associado é obrigatório!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }
        // Recupera o usuário completo do banco de dados pelo ID
        Optional<PilotoModel> piloto1Optional = pilotoService.buscarPorId(piloto1Id);
        if (piloto1Optional.isEmpty()) {
          rm.setMensagem("piloto1 associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        PilotoModel piloto1Persistido = piloto1Optional.get();
        dm.setPiloto1(piloto1Persistido);

        Optional<PilotoModel> piloto2Optional = pilotoService.buscarPorId(piloto2Id);
        if (piloto2Optional.isEmpty()) {
          rm.setMensagem("piloto2 associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        PilotoModel piloto2Persistido = piloto2Optional.get();
        dm.setPiloto2(piloto2Persistido);

        Optional<CampeonatoModel> campeonatoOptional = campeonatoService.buscarPorId(campeonatoId);
        if (campeonatoOptional.isEmpty()) {
          rm.setMensagem("campeonato associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }

        CampeonatoModel campeonatoPersistido = campeonatoOptional.get();
        dm.setCampeonato(campeonatoPersistido);

        // Decide se é uma operação de cadastrar ou alterar
        if (acao.equals("cadastrar")) {
          return ds.cadastrarAlterar(dm, "cadastrar");
        } else {
          return ds.cadastrarAlterar(dm, "alterar");
        }

      }
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @PostMapping("/disputa/alterar")
  public ResponseEntity<?> alterar(@RequestBody DisputaModel dm, @RequestParam String acao,
      @RequestParam Long pilotoVencedorId) {
    RespostaModel rm = new RespostaModel();

    try {
      if (pilotoVencedorId == null) {
        rm.setMensagem("ID do piloto associado é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      } else {
        // Recupera o usuário completo do banco de dados pelo ID
        Optional<PilotoModel> piloto1Optional = pilotoService.buscarPorId(pilotoVencedorId);
        if (piloto1Optional.isEmpty()) {
          rm.setMensagem("piloto1 associado não encontrado!");
          return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        }
        
        PilotoModel piloto1Persistido = piloto1Optional.get();
        dm.setPilotoVencedor(piloto1Persistido);
        disputaService.listarApostasPorDisputa(dm);
        // Decide se é uma operação de cadastrar ou alterar
        if (acao.equals("cadastrar")) {
          return ds.cadastrarAlterar(dm, "cadastrar");
        } else {
          return ds.cadastrarAlterar(dm, "alterar");
        }

      }
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @Autowired
  UsuarioService usuarioService;
  @Autowired
  UsuarioRepository usuarioRepository;
  @Autowired
  DisputaRepository dir;

  @Autowired
  ApostaService apostaService;

  @GetMapping("/disputa/listarApostas/{idDisputa}")
  public ResponseEntity<?> listarApostasPorDisputa(@PathVariable Long idDisputa) {
    Optional<DisputaModel> disputaOptional = dir.findById(idDisputa);

    if (disputaOptional.isPresent()) {
      DisputaModel disputa = disputaOptional.get();
      List<ApostaModel> apostas = disputa.getAposta();

      for (ApostaModel aposta : apostas) {
        if (disputa.getEmAndamento() == false) {
          if (aposta.getProcesso() == '1') {
            if (aposta.getPiloto().getId() == disputa.getPilotoVencedor().getId()) {
              // A aposta é vencedora

              // Atualizar saldo do usuário
              Long idUser = aposta.getUsuario().getId();
              Optional<UsuarioModel> userOptional = usuarioRepository.findById(idUser);

              if (userOptional.isPresent()) {
                UsuarioModel user = userOptional.get();
                // Atualize o saldo conforme necessário
                // Por exemplo, adicionando 6.0 ao saldo
                user.setSaldo(user.getSaldo() + 7.5f);
                usuarioService.cadastrarAlterar(user, "alterar");
                aposta.setProcesso('3');
                apostaService.cadastrarAlterar(aposta, "alterar");
              }
            }
          }
        }
      }

      // Retorna um OK se a operação foi bem-sucedida
      return ResponseEntity.ok().build();
    } else {
      // Retorna um NOT_FOUND se a disputa não for encontrada
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/disputa/listar")
  public Iterable<DisputaModel> listar() {
    return ds.listar();
  }

  @GetMapping("/disputa/")
  public String rota() {
    return "Api de produtos funcionando!";
  }

  @Autowired
  private DisputaService disputaService;

  @GetMapping("/disputa/procurar/{id}")
  public ResponseEntity<DisputaModel> buscarPorId(@PathVariable Long id) {
    Optional<DisputaModel> disputa = disputaService.buscarPorId(id);

    return disputa.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
