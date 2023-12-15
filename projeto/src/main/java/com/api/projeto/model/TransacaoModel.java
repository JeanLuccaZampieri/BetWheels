package com.api.projeto.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_Transacao")
public class TransacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Long id;
    @Column(name = "valor", nullable = false)
    private Float valor;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(name = "dataTransacao", nullable = false)
    private Date dataTransacao;
    @Column(name = "tipo_id")
    private Character tipo;
    @Column(name = "cartao")
    private Character cartao;
    @ManyToOne
    @JoinColumn(name = "usuario_id", unique = false)
    private UsuarioModel usuario;

}
