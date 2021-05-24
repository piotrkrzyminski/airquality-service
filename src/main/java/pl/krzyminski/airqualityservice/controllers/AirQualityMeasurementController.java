package pl.krzyminski.airqualityservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;
import pl.krzyminski.airqualityservice.facades.AirQualityFacade;

/**
 * REST controller for air quality measurements.
 *
 * @author Piotr Krzymiński
 */
@RestController
@RequestMapping("/measurements")
public class AirQualityMeasurementController {

    private static final Logger LOG = LoggerFactory.getLogger(AirQualityMeasurementController.class);

    private final AirQualityFacade airQualityFacade;

    public AirQualityMeasurementController(@Qualifier("airlyAirQualityFacade") AirQualityFacade airQualityFacade) {
        this.airQualityFacade = airQualityFacade;
    }

    @GetMapping("/installation")
    public ResponseEntity<AirQualityMeasurementDto> getMeasurementsForInstallation(
            @RequestParam(name = "installationId") int installationId) {
        LOG.debug("Looking for air measurements from installation with id {}", installationId);
        return ResponseEntity.of(airQualityFacade.getAirMeasurementForInstallation(installationId));
    }
}
