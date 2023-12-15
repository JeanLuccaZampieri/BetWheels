package com.api.projeto.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_usuario")
public class UsuarioModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long id;
  @Column(name = "nome", nullable = false, length = 50, unique = true)
  private String nome;
  @Column(name = "cpf", nullable = false, length = 11, unique = true)
  private String cpf;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "dataNascimento", nullable = false)
  private LocalDate dataNascimento;
  @Column(name = "senha", nullable = false)
  private String senha;
  @Column(name = "numeroTelefone", nullable = false)
  private String numeroTelefone;
  @Column(name = "saldo", nullable = false)
  private float saldo;
  @Column (name= "administrador", nullable = false)
  private boolean administrador;
  @JsonIgnore
  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<TransacaoModel> trasacao;
  @JsonIgnore
  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ApostaModel> aposta;
}
