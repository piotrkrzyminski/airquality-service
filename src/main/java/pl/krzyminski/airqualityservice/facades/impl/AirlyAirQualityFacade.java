package pl.krzyminski.airqualityservice.facades.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;
import pl.krzyminski.airqualityservice.facades.AirQualityFacade;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;
import pl.krzyminski.airqualityservice.services.AirQualityService;

import java.util.Optional;

/**
 * Implementation of {@link AirQualityFacade} for Airly.
 *
 * @author Piotr Krzymiński
 */
@Component
public class AirlyAirQualityFacade implements AirQualityFacade {

    private static final Logger LOG = LoggerFactory.getLogger(AirlyAirQualityFacade.class);

    private final AirQualityService airQualityService;
    private final ConversionService conversionService;

    public AirlyAirQualityFacade(@Qualifier("airlyAirQualityService") AirQualityService airQualityService,
                                 ConversionService conversionService) {

        this.airQualityService = airQualityService;
        this.conversionService = conversionService;
    }

    @Override
    public Optional<AirQualityMeasurementDto> getAirMeasurementForInstallation(int installationId) {
        LOG.debug("Looking for air quality measurement for installation {}", installationId);
        Optional<AirQualityMeasurement> airQualityMeasurement = airQualityService
                .getAirQualityMeasurementFromInstallation(installationId);
        if (airQualityMeasurement.isEmpty()) {
            LOG.error("Could not find air quality measurements");
            return Optional.empty();
        }

        return Optional.ofNullable(conversionService.convert(airQualityMeasurement.get(), AirQualityMeasurementDto.class));
    }
}
