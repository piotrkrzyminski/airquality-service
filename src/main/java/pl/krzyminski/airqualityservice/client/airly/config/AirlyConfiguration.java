package pl.krzyminski.airqualityservice.client.airly.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "airly")
@Getter
@Setter
public class AirlyConfiguration {

    String server;
    String measurement;
    String apiKey;
}