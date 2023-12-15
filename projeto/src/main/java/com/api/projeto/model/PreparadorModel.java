package com.api.projeto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_Preparador")
public class PreparadorModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_preparador")
  private Long id;
  @Column(name = "nome", nullable = false, length = 50, unique = true)
  private String nome;
  @JsonIgnore
  @OneToMany(mappedBy = "preparador", cascade = CascadeType.ALL)
  private List<CarroModel> carro;
}
