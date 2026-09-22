package it.its.catalogoservice.service;

import it.its.catalogoservice.client.ProdottoClient;
import it.its.catalogoservice.client.ProdottoJson;
import it.its.catalogoservice.domain.Categoria;
import it.its.catalogoservice.domain.Prodotto;
import it.its.catalogoservice.repository.CategoriaRepository;
import it.its.catalogoservice.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ImportService {

    private static final String SKU_PROMO_OMAGGIO = "PROMO-OMAGGIO";
    private static final Pattern PATTERN_SKU = Pattern.compile("^([A-Za-z]{2})\\d+$");

    private final ProdottoClient client;
    private final ProdottoRepository prodottoRepository;
    private final CategoriaRepository categoriaRepository;


    public ImportService(ProdottoClient client, ProdottoRepository prodottoRepository, CategoriaRepository categoriaRepository) {
        this.client = client;
        this.prodottoRepository = prodottoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public EsitoImport importa(){
        Map<String, ProdottoJson> scaricate= client.scarica();
        if (scaricate==null|| scaricate.isEmpty()){
            return new EsitoImport(0,0,0);
        }

        int scartati = 0;
        int inseriti = 0;
        int giaPresenti = 0;

        for (ProdottoJson json : scaricate.values()) {
            String sku = json.sku();
            if (sku == null || sku.isBlank()) {
                scartati++;
                continue;
            }

            Categoria categoria = risolviCategoria(sku);
            if (categoria == null) {
                scartati++;
                continue;
            }

            if (prodottoRepository.existsBySku(sku)) {
                giaPresenti++;
                continue;
            }

            Prodotto prodotto = new Prodotto(json.nome(), sku, json.prezzo(),
                    json.disponibile(), json.produttore(), json.ean(), categoria);
            prodottoRepository.save(prodotto);
            inseriti++;
        }

        return new EsitoImport(scartati, inseriti, giaPresenti);
    }

    private Categoria risolviCategoria(String sku) {
        if (SKU_PROMO_OMAGGIO.equals(sku)) {
            return categoriaRepository.findByPrefisso(null).orElse(null);
        }

        Matcher matcher = PATTERN_SKU.matcher(sku);
        if (!matcher.matches()) {
            return null;
        }

        String prefisso = matcher.group(1).toUpperCase();
        return categoriaRepository.findByPrefisso(prefisso).orElse(null);
    }
}
