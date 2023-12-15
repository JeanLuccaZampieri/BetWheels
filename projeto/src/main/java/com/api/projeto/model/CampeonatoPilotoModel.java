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

@Data
@Entity
@Table(name = "tb_campeonato_piloto")
public class CampeonatoPilotoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private CampeonatoModel campeonato;

    @ManyToOne
    @JoinColumn(name = "piloto_id")
    private PilotoModel piloto;

    @Column(name = "ativo")
    private boolean ativo;

    // Construtores, getters, setters, etc.
}
