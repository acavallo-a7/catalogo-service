package it.its.catalogoservice.repository;

import it.its.catalogoservice.domain.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto,Long> {
    boolean existsBySku(String sku);
}
