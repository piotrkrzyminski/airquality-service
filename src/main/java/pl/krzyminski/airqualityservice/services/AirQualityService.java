package pl.krzyminski.airqualityservice.services;

import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

import java.util.Optional;

public interface AirQualityService {

    Optional<AirQualityMeasurement> getAirQualityMeasurementFromInstallation(int installationId);
}
