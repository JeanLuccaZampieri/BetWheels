package com.api.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ManyToOne;

@Data
@Entity
@Table(name = "TB_Piloto")
public class PilotoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_piloto")
  private Long id;
  @Column(name = "nome", nullable = false, length = 50, unique = true)
  private String nome;
  @ManyToOne
  @JoinColumn(name = "carro_id", unique = false)
  private CarroModel carro;
  @JsonIgnore
  @OneToMany(mappedBy = "piloto", cascade = CascadeType.ALL)
  private List<CampeonatoPilotoModel> campeonatoPilotos;
  @JsonIgnore
  @OneToMany(mappedBy = "piloto1", cascade = CascadeType.ALL)
  private List<DisputaModel> disputas1;
  @JsonIgnore
  @OneToMany(mappedBy = "piloto2", cascade = CascadeType.ALL)
  private List<DisputaModel> disputas2;
  @JsonIgnore
  @OneToMany(mappedBy = "pilotoVencedor", cascade = CascadeType.ALL)
  private List<DisputaModel> disputasVencidas;

  @ManyToOne
  @JoinColumn(name = "equipe_id", unique = false)
  private EquipeModel equipe;
  @JsonIgnore
  @OneToMany(mappedBy = "piloto", cascade = CascadeType.ALL)
  private List<ApostaModel> aposta;
}
