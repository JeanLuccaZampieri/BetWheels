package com.api.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.TransacaoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.TransacaoRepository;

@Service
public class TransacaoService {

  @Autowired
  private RespostaModel rm;

  @Autowired
  private TransacaoRepository tr;


  // Matodo para cadastrar e alterar transacao
  public ResponseEntity<?> cadastrarAlterar(TransacaoModel cm, String acao) {
    try {
      if (cm.getValor().equals("")) {
        rm.setMensagem("O valor da transação é obrigatório!");
        return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
      }

      TransacaoModel transacaoSalva = tr.save(cm);
      return new ResponseEntity<TransacaoModel>(transacaoSalva, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace(); // Isso imprimirá o stack trace no console
      rm.setMensagem("Erro ao cadastrar/alterar transaçãoAAAAAAAAA.");
      return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<RespostaModel> remover(long id) {
    tr.deleteById(id);
    rm.setMensagem("O extrato foi removido com sucesso");
    return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
  }

  // Metodo para listar todos os transacaos
  public Iterable<TransacaoModel> listar() {
    return tr.findAll();
  }

  public Optional<TransacaoModel> buscarPorId(Long id) {
    return tr.findById(id);
  }

}
