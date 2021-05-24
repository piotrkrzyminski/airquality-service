package pl.krzyminski.airqualityservice.client.airly.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.krzyminski.airqualityservice.client.airly.config.AirlyConfiguration;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;

import java.net.URI;
import java.util.Optional;

/**
 * Rest client for Airly measurements.
 *
 * @author Piotr Krzymiński
 */
@Component
public class AirlyMeasurementsClient extends AbstractAirlyClient {

    private static final Logger LOG = LoggerFactory.getLogger(AirlyMeasurementsClient.class);

    private final RestTemplate restTemplate;

    public AirlyMeasurementsClient(AirlyConfiguration airlyConfiguration, RestTemplate restTemplate) {
        super(airlyConfiguration);
        this.restTemplate = restTemplate;
    }

    /**
     * Get measurement for concrete installation given by provided argument. In case when installation does not
     * exists or could not fetch it's measurement then return empty optional.
     *
     * @param installationId installation identifier.
     * @return optional of measurement for provided installation.
     */
    public Optional<Measurement> getMeasurementForInstallation(@NonNull Integer installationId) {
        LOG.debug("Attempting to get measurement for installation with id {}", installationId);

        URI uri = UriComponentsBuilder
                .fromUriString(getMeasurementsUrl() + "/installation")
                .queryParam("installationId", installationId)
                .build()
                .toUri();

        try {
            ResponseEntity<Measurement> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
                    new HttpEntity<>(getHttpHeader()), Measurement.class);
            Measurement measurement = responseEntity.getBody();
            LOG.debug("Received measurement response: [{}]", measurement);

            return Optional.ofNullable(measurement);
        } catch (RestClientResponseException e) {
            LOG.error("Could not receive measurement for installation with id {}. Reason: {}",
                    installationId, e.getMessage());
            return Optional.empty();
        }
    }

    private String getMeasurementsUrl() {
        return getAirlyConfiguration().getServer() + getAirlyConfiguration().getMeasurement();
    }
}
