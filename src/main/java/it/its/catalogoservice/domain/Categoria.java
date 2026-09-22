package it.its.catalogoservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "categorie")
@Getter
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2, unique = true)
    private String prefisso;
    @Column(nullable = false, unique = true)
    private String nome;

    public Categoria(String prefisso, String nome){
        this.prefisso = prefisso;
        this.nome = nome;
    }
}
