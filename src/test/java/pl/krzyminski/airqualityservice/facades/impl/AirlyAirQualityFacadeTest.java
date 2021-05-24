package pl.krzyminski.airqualityservice.facades.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;
import pl.krzyminski.airqualityservice.services.AirQualityService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link AirlyAirQualityFacade}.
 *
 * @author Piotr Krzymiński
 */
@ExtendWith(MockitoExtension.class)
class AirlyAirQualityFacadeTest {

    @InjectMocks
    private AirlyAirQualityFacade airQualityFacade;

    @Mock
    private AirQualityService airQualityService;
    @Mock
    private ConversionService conversionService;

    @Test
    public void testGetAirMeasurementForInstallationNotFound() {
        int installationId = 100;

        Mockito.doReturn(Optional.empty())
                .when(airQualityService)
                .getAirQualityMeasurementFromInstallation(installationId);

        assertTrue(airQualityFacade.getAirMeasurementForInstallation(installationId).isEmpty());
    }

    @Test
    public void testGetAirMeasurementForInstallationSuccess() {
        int installationId = 100;

        AirQualityMeasurement airQualityMeasurement = Mockito.mock(AirQualityMeasurement.class);
        AirQualityMeasurementDto airQualityMeasurementDto = Mockito.mock(AirQualityMeasurementDto.class);

        Mockito.doReturn(Optional.of(airQualityMeasurement))
                .when(airQualityService)
                .getAirQualityMeasurementFromInstallation(installationId);
        Mockito.doReturn(airQualityMeasurementDto)
                .when(conversionService)
                .convert(airQualityMeasurement, AirQualityMeasurementDto.class);

        assertTrue(airQualityFacade.getAirMeasurementForInstallation(installationId).isPresent());
    }
}