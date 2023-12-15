package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.RespostaModel;
import com.api.projeto.model.UsuarioModel;
import com.api.projeto.repository.UsuarioRepository;


@Service
public class UsuarioService {
  @Autowired
  private RespostaModel rm;

  @Autowired
  private UsuarioRepository ur;

  // Matodo para cadastrar e alterar carro
  public ResponseEntity<?> cadastrarAlterar(UsuarioModel um, String acao) {

    if (um.getNome().equals("")) {
      rm.setMensagem("O nome do usuario é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else if (um.getSenha().equals("")) {
      rm.setMensagem("A senha do usuario é obrigatório!");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
    } else {
      if (acao.equals("cadastrar")) {
        return new ResponseEntity<UsuarioModel>(ur.save(um), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<UsuarioModel>(ur.save(um), HttpStatus.OK);
      }
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    ur.deleteById(id);
    rm.setMensagem("O usuario foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os carros
  public Iterable<UsuarioModel> listar() {
    return ur.findAll();
  }

  public Optional<UsuarioModel> buscarPorId(Long id) {
    return ur.findById(id);
  }

}
