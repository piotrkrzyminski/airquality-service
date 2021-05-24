package pl.krzyminski.airqualityservice.client.airly.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import pl.krzyminski.airqualityservice.client.airly.config.AirlyConfiguration;

import java.util.Collections;

@RestController
public class AbstractAirlyClient {

    private final AirlyConfiguration airlyConfiguration;

    public AbstractAirlyClient(AirlyConfiguration airlyConfiguration) {
        this.airlyConfiguration = airlyConfiguration;
    }

    protected HttpHeaders getHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("apikey", airlyConfiguration.getApiKey());

        return httpHeaders;
    }

    public AirlyConfiguration getAirlyConfiguration() {
        return airlyConfiguration;
    }
}
