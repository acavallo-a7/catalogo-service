package it.its.catalogoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
@Configuration
public class ProdottoHttpConfig {

    private static final Duration TIMEOUT_CONNESSIONE = Duration.ofSeconds(2);

    private static final Duration TIMEOUT_LETTURA = Duration.ofSeconds(3);

    @Bean
    RestClient catologRestClient(RestClient.Builder builder,
                                 @Value("${catalogo.importer.url}") String baseUrl) {
        // raw.githubusercontent.com serve i .json con Content-Type text/plain:
        // il converter Jackson di default accetta solo application/json.
        MappingJackson2HttpMessageConverter jsonAncheSuTextPlain = new MappingJackson2HttpMessageConverter();
        jsonAncheSuTextPlain.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));

        return builder
                .baseUrl(baseUrl)
                .requestFactory(fabbricaConTimeout())
                .messageConverters(converters -> converters.add(0, jsonAncheSuTextPlain))
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
