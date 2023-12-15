package com.api.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.ApostaModel;
import com.api.projeto.model.DisputaModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.repository.ApostaRepository;
import com.api.projeto.repository.DisputaRepository;
import com.api.projeto.repository.UsuarioRepository;

@Service
public class DisputaService {

  @Autowired
  private RespostaModel rm;

  @Autowired
  private DisputaRepository dir;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(DisputaModel dim, String acao) {

    if (dim.getCampeonato().equals("")) {
      rm.setMensagem("A disputa do campeonato é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<DisputaModel>(dir.save(dim), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<DisputaModel>(dir.save(dim), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    dir.deleteById(id);
    rm.setMensagem("A disputa foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<DisputaModel> listar() {
    return dir.findAll();
  }

  public Optional<DisputaModel> buscarPorId(Long id) {
    return dir.findById(id);
  }

  @Autowired
  private ApostaRepository apostaRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public ResponseEntity<?> listarApostasPorDisputa(DisputaModel disputa) {

    Optional<UsuarioModel> userOptional = Optional.empty();
    if (disputa != null) {
      // DisputaModel disputa = disputaOptional.get();
      List<ApostaModel> apostas = disputa.getAposta();
      for (ApostaModel aposta : apostas) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(aposta);
        if (aposta.getTipoAposta() == disputa.getModoVitoria()) {
          if (aposta.getPiloto().getId() == disputa.getPilotoVencedor().getId()) {
            Long idUser = aposta.getUsuario().getId();
            userOptional = usuarioRepository.findById(idUser);

            // Verifique se o usuário foi encontrado antes de acessar o saldo
            if (userOptional.isPresent()) {
              UsuarioModel user = userOptional.get();
              float valorAposta = (float) (aposta.getValor() * 1.5);
              float saldoInicial = user.getSaldo();
              float saldoAtual = saldoInicial + valorAposta;
              user.setSaldo(saldoAtual);
              usuarioService.cadastrarAlterar(user, "alterar");
            }
          }
        }
      }

      return new ResponseEntity<List<ApostaModel>>(apostas, HttpStatus.OK);
    } else {
      rm.setMensagem("Disputa não encontrada");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.NOT_FOUND);
    }
  }

}
