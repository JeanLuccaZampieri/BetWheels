package com.api.projeto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_Carro")
public class CarroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private Long id;
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;
    @Column(name = "marca", nullable = false, length = 50)
    private String marca;
    @JsonIgnore
    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL)
    private List<PilotoModel> pílotos;
    @ManyToOne
    @JoinColumn(name = "preparador_id")
    private PreparadorModel preparador;
}
