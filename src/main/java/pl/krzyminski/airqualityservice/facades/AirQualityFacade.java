package pl.krzyminski.airqualityservice.facades;

import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;

import java.util.Optional;

/**
 * Facade for air quality.
 *
 * @author Piotr Krzymiński
 */
public interface AirQualityFacade {

    /**
     * Get air quality measurements from installation with provided id.
     *
     * @param installationId installation identifier.
     * @return air measurements from installation.
     */
    Optional<AirQualityMeasurementDto> getAirMeasurementForInstallation(int installationId);
}
