package com.api.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.projeto.model.CampeonatoPilotoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.repository.CampeonatoPilotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoPilotoService {

    @Autowired
  private RespostaModel rm;
    @Autowired
    private CampeonatoPilotoRepository campeonatoPilotoRepository;

    public ResponseEntity<?> cadastrarAlterar(CampeonatoPilotoModel cm, String acao) {

        if (cm.getCampeonato().equals("")) {
            rm.setMensagem("O campeonato é obrigatório!");
            return new ResponseEntity<RespostaModel>(HttpStatus.BAD_REQUEST);
        } else {
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<CampeonatoPilotoModel>(campeonatoPilotoRepository.save(cm), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<CampeonatoPilotoModel>(campeonatoPilotoRepository.save(cm), HttpStatus.OK);
            }
        }
    }

    public CampeonatoPilotoModel alterarCampeonatoPiloto(Long id, CampeonatoPilotoModel campeonatoPiloto) {
        CampeonatoPilotoModel associacaoExistente = campeonatoPilotoRepository.findById(id).orElse(null);
        if (associacaoExistente != null) {
            // Atualiza os campos necessários
            associacaoExistente.setAtivo(campeonatoPiloto.isAtivo());
            // Lógica de validação, se necessário

            return campeonatoPilotoRepository.save(associacaoExistente);
        }
        return null;
    }

    public List<CampeonatoPilotoModel> listarCampeonatoPilotos() {
        return campeonatoPilotoRepository.findAll();
    }

    public List<CampeonatoPilotoModel> listarPilotosAtivosPorCampeonato(Long campeonatoId) {
        return campeonatoPilotoRepository.findByCampeonatoIdAndAtivoTrue(campeonatoId);
    }

    public CampeonatoPilotoModel buscarPorPilotoIdECampeonatoId(Long pilotoId, Long campeonatoId) {
        Optional<CampeonatoPilotoModel> campeonatoPilotoOptional = 
            campeonatoPilotoRepository.findByPilotoIdAndCampeonatoId(pilotoId, campeonatoId);

        return campeonatoPilotoOptional.orElse(null);
    }


    // Outros métodos e lógica necessários
}