package com.api.projeto.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_Aposta")
public class ApostaModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_aposta")
  private Long id;
  @Column(name = "valor", nullable = false)
  private Float valor;
  @Column(name = "tipoAposta", nullable = false)
  private Character tipoAposta;
  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private UsuarioModel usuario;
  @ManyToOne
  @JoinColumn(name = "piloto_id")
  private PilotoModel piloto;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "disputa_id")
  private DisputaModel disputa;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
  @Column(name = "dataAposta", nullable = false)
  private Date dataAposta;
  @Column(name = "processo", nullable = false)
  private Character processo;
}
