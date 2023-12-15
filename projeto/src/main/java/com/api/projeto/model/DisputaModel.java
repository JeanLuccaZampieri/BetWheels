package com.api.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.OneToMany;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;

@Entity
@Data
@Table(name = "TB_Disputa")
public class DisputaModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_disputa")
  private Long id;
  @Column(name = "modoVitoria", nullable = true)
  private Character modoVitoria;
  @Column(name = "emAndamento")
  private Boolean emAndamento ;
  @ManyToOne
  @JoinColumn(name = "campeonato_id")
  private CampeonatoModel campeonato;
 
  @OneToMany(mappedBy = "disputa", cascade = CascadeType.ALL)
  private List<ApostaModel> aposta;

  @ManyToOne
  @JoinColumn(name = "piloto1_id")
  private PilotoModel piloto1;
  @ManyToOne
  @JoinColumn(name = "piloto2_id")
  private PilotoModel piloto2;
  @ManyToOne
  @JoinColumn(name = "pilotoVencedor_id")
  private PilotoModel pilotoVencedor;
}
