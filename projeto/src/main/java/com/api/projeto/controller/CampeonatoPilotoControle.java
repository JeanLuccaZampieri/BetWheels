package com.api.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.projeto.model.CampeonatoModel;
import com.api.projeto.model.CampeonatoPilotoModel;
import com.api.projeto.model.PilotoModel;
import com.api.projeto.model.RespostaModel;
import com.api.projeto.service.CampeonatoPilotoService;
import com.api.projeto.service.CampeonatoService;
import com.api.projeto.service.PilotoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class CampeonatoPilotoControle {

    @Autowired
    private CampeonatoPilotoService campeonatoPilotoService;

    // Endpoint para cadastrar uma nova associação Campeonato-Piloto
    @Autowired
    private PilotoService pilotoService;

    @Autowired
    private CampeonatoService campeonatoService;

    @PostMapping("/campeonatoPiloto/cadastrar-alterar")
    public ResponseEntity<?> cadastrarAlterar(@RequestBody CampeonatoPilotoModel cm,
            @RequestParam String acao,
            @RequestParam Long pilotoId,
            @RequestParam Long campeonatoId) {
        RespostaModel rm = new RespostaModel();

        try {
            if (campeonatoId == null) {
                rm.setMensagem("O campeonato é obrigatório!");
                return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
            } else {
                // Verifica se o ID do usuário associado está presente
                if (pilotoId == null) {
                    rm.setMensagem("ID do piloto associado é obrigatório!");
                    return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
                }

                // Recupera o usuário completo do banco de dados pelo ID
                Optional<PilotoModel> pilotoOptional = pilotoService.buscarPorId(pilotoId);
                if (pilotoOptional.isEmpty()) {
                    rm.setMensagem("Usuário associado não encontrado!");
                    return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
                }

                PilotoModel pilotoPersistido = pilotoOptional.get();
                cm.setPiloto(pilotoPersistido);

                Optional<CampeonatoModel> campeonatoOptional = campeonatoService.buscarPorId(campeonatoId);
                if (campeonatoOptional.isEmpty()) {
                    rm.setMensagem("Usuário associado não encontrado!");
                    return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
                }

                CampeonatoModel campeonatoPersistido = campeonatoOptional.get();
                cm.setCampeonato(campeonatoPersistido);

                // Verifica se o registro já existe no objeto CampeonatoPilotoModel
                if (campeonatoPersistido.getCampeonatoPilotos().stream()
                        .anyMatch(cp -> cp.getPiloto().getId().equals(pilotoId))) {
                    rm.setMensagem("O registro com pilotoId e campeonatoId já existe!");
                    return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
                }

                // Decide se é uma operação de cadastrar ou alterar
                if (acao.equals("cadastrar")) {
                    return campeonatoPilotoService.cadastrarAlterar(cm, "cadastrar");
                } else {
                    return campeonatoPilotoService.cadastrarAlterar(cm, "alterar");
                }
            }
        } catch (Exception e) {
            // Lida com exceções gerais
            rm.setMensagem("Erro durante a execução da operação");
            return new ResponseEntity<RespostaModel>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para alterar uma associação Campeonato-Piloto existente
    @PutMapping("/campeonatoPiloto/alterar")
    public ResponseEntity<CampeonatoPilotoModel> alterarCampeonatoPiloto(@PathVariable Long id,
            @RequestBody CampeonatoPilotoModel campeonatoPiloto) {
        CampeonatoPilotoModel associacaoAtualizada = campeonatoPilotoService.alterarCampeonatoPiloto(id,
                campeonatoPiloto);
        if (associacaoAtualizada != null) {
            return new ResponseEntity<>(associacaoAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para listar todas as associações Campeonato-Piloto
    @GetMapping("/campeonatoPiloto/listar")
    public ResponseEntity<List<CampeonatoPilotoModel>> listarCampeonatoPilotos() {
        List<CampeonatoPilotoModel> listaAssociacoes = campeonatoPilotoService.listarCampeonatoPilotos();
        return new ResponseEntity<>(listaAssociacoes, HttpStatus.OK);
    }

    // Endpoint para listar todos os pilotos ativos por campeonato
    @GetMapping("/campeonatoPiloto/ativos/{campeonatoId}")
    public ResponseEntity<List<CampeonatoPilotoModel>> listarPilotosAtivosPorCampeonato(
            @PathVariable Long campeonatoId) {
        List<CampeonatoPilotoModel> pilotosAtivos = campeonatoPilotoService
                .listarPilotosAtivosPorCampeonato(campeonatoId);
        return new ResponseEntity<>(pilotosAtivos, HttpStatus.OK);
    }

    @GetMapping("/campeonatoPiloto/listarPilotosPorCampeonato/{campeonatoId}")
    public ResponseEntity<List<PilotoModel>> listarPilotosPorCampeonato(@PathVariable Long campeonatoId) {
        RespostaModel rm = new RespostaModel();
        try {
            Optional<CampeonatoModel> campeonatoOptional = campeonatoService.buscarPorId(campeonatoId);
            if (campeonatoOptional.isEmpty()) {
                rm.setMensagem("Campeonato não encontrado!");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            CampeonatoModel campeonato = campeonatoOptional.get();
            List<CampeonatoPilotoModel> associacoes = campeonato.getCampeonatoPilotos();

            // Filtrar pilotos ativos
            List<PilotoModel> pilotos = associacoes.stream()
                    .filter(campeonatoPiloto -> campeonatoPiloto.isAtivo())
                    .map(CampeonatoPilotoModel::getPiloto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(pilotos, HttpStatus.OK);
        } catch (Exception e) {
            rm.setMensagem("Erro durante a execução da operação");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/campeonatoPiloto/buscar")
    public ResponseEntity<CampeonatoPilotoModel> buscarCampeonatoPilotoPorIds(
            @RequestParam Long pilotoId,
            @RequestParam Long campeonatoId) {
    
        CampeonatoPilotoModel campeonatoPiloto = campeonatoPilotoService.buscarPorPilotoIdECampeonatoId(pilotoId, campeonatoId);
    
        if (campeonatoPiloto != null) {
            return new ResponseEntity<>(campeonatoPiloto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Outros endpoints, métodos e lógica necessários
}