package pl.krzyminski.airqualityservice.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;
import pl.krzyminski.airqualityservice.client.airly.rest.AirlyMeasurementsClient;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;
import pl.krzyminski.airqualityservice.services.AirQualityService;

import java.util.Objects;
import java.util.Optional;

/**
 * Air quality service implementation dedicated to Airly service.
 *
 * @author Piotr Krzymiński
 */
@Service
public class AirlyAirQualityService implements AirQualityService {

    private static final Logger LOG = LoggerFactory.getLogger(AirlyAirQualityService.class);

    private final AirlyMeasurementsClient airlyMeasurementsClient;
    private final ConversionService conversionService;
    private final CacheManager cacheManager;

    public AirlyAirQualityService(AirlyMeasurementsClient airlyMeasurementsClient,
                                  ConversionService conversionService,
                                  CacheManager cacheManager) {

        this.airlyMeasurementsClient = airlyMeasurementsClient;
        this.conversionService = conversionService;
        this.cacheManager = cacheManager;
    }

    @Override
    @Cacheable(value = "airMeasurementInstallation", key = "#installationId")
    public Optional<AirQualityMeasurement> getAirQualityMeasurementFromInstallation(int installationId) {
        LOG.debug("Attempting to get air measurement from installation {}", installationId);
        Optional<Measurement> measurement = airlyMeasurementsClient.getMeasurementForInstallation(installationId);
        if (measurement.isEmpty()) {
            LOG.warn("Could not obtain measurement for installation {}", installationId);
            return Optional.empty();
        }

        return Optional.ofNullable(conversionService.convert(measurement.get(), AirQualityMeasurement.class));
    }

    @Scheduled(fixedRate = 600000)
    public void evictCache() {
        Objects.requireNonNull(cacheManager.getCache("airMeasurementInstallation")).clear();
    }
}
