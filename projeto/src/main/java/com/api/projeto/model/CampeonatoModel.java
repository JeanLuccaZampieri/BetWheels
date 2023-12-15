package com.api.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.OneToMany;

@Data
@Entity
@Table(name = "TB_Campeonato")
public class CampeonatoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_campeonato")
  private Long id;
  @Column(name = "nome", nullable = false, length = 50, unique = true)
  private String nome;
  @JsonIgnore
  @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL)
  private List<DisputaModel> disputa;
  @JsonIgnore
  @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL)
  private List<CampeonatoPilotoModel> campeonatoPilotos;

}
