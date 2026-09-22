package it.its.catalogoservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

@Component
@Slf4j
public class ProdottoClient {

    private static final String SERVIZIO = "catalogo-service";
    private final RestClient http;
    public ProdottoClient(RestClient http){
        this.http = http;
    }

    public Map<String,ProdottoJson> scarica() {

        var tipo=new ParameterizedTypeReference<Map<String,ProdottoJson>>(){};
        Map<String,ProdottoJson> prodotti= http.get()
                .retrieve()
                .body(tipo);

        return prodotti;
    }
}
