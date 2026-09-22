package it.its.catalogoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
@Configuration
public class ProdottoHttpConfig {

    private static final Duration TIMEOUT_CONNESSIONE = Duration.ofSeconds(2);

    private static final Duration TIMEOUT_LETTURA = Duration.ofSeconds(3);

    @Bean
    RestClient catologRestClient(RestClient.Builder builder,
                                 @Value("${catalogo.importer.url}") String baseUrl) {
        return builder
                .baseUrl(baseUrl)
                .requestFactory(fabbricaConTimeout())
                .build();
    }

    private static ClientHttpRequestFactory fabbricaConTimeout() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(TIMEOUT_CONNESSIONE)
                .build();
        JdkClientHttpRequestFactory fabbrica = new JdkClientHttpRequestFactory(httpClient);
        fabbrica.setReadTimeout(TIMEOUT_LETTURA);
        return fabbrica;
    }
}
