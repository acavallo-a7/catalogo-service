package it.its.catalogoservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "prodotti")
@Getter
@NoArgsConstructor
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false)
    private BigDecimal prezzo;
    @Column(nullable = false)
    private boolean disponibile;
    @Column(nullable = false)
    private String produttore;
    private String ean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Prodotto(String nome, String sku, BigDecimal prezzo, boolean disponibile,
                     String produttore, String ean, Categoria categoria) {
        this.nome = nome;
        this.sku = sku;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
        this.produttore = produttore;
        this.ean = ean;
        this.categoria = categoria;
    }
}
