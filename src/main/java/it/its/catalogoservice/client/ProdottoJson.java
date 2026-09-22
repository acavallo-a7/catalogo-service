package it.its.catalogoservice.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdottoJson(
        @JsonProperty("nome_prodotto")
        String nome,
        @JsonProperty("codice_sku")
        String sku,
        @JsonProperty("prezzo_listino")
        BigDecimal prezzo,
        boolean disponibile,
        String produttore,
        @JsonProperty("codice_ean")
        String ean
) {
}
