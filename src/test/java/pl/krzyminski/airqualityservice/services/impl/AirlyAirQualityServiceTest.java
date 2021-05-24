package pl.krzyminski.airqualityservice.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;
import pl.krzyminski.airqualityservice.client.airly.rest.AirlyMeasurementsClient;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link AirlyAirQualityService}.
 *
 * @author Piotr Krzymiński
 */
@ExtendWith(MockitoExtension.class)
class AirlyAirQualityServiceTest {

    @InjectMocks
    private AirlyAirQualityService airQualityService;

    @Mock
    private AirlyMeasurementsClient airlyMeasurementsClient;
    @Mock
    private ConversionService conversionService;

    @Test
    public void testGetAirQualityMeasurementFromInstallationNotFound() {
        int installationId = 192;

        Mockito.doReturn(Optional.empty())
                .when(airlyMeasurementsClient)
                .getMeasurementForInstallation(installationId);

        assertTrue(airQualityService.getAirQualityMeasurementFromInstallation(installationId).isEmpty());
    }

    @Test
    public void testGetAirQualityMeasurementFromInstallationSuccess() {
        int installationId = 192;

        Measurement measurement = createMockMeasurement();

        Mockito.doReturn(Optional.of(measurement))
                .when(airlyMeasurementsClient)
                .getMeasurementForInstallation(installationId);

        AirQualityMeasurement airQualityMeasurement = Mockito.mock(AirQualityMeasurement.class);
        Mockito.doReturn(airQualityMeasurement)
                .when(conversionService)
                .convert(measurement, AirQualityMeasurement.class);

        assertTrue(airQualityService.getAirQualityMeasurementFromInstallation(installationId).isPresent());
    }

    private Measurement createMockMeasurement() {
        Measurement measurement = new Measurement();
        Measurement.Current current = new Measurement.Current();
        Measurement.Current.Index index = new Measurement.Current.Index();
        index.setAdvice("Dummy advice");
        index.setDescription("Dummy description");
        index.setLevel("LOW");
        index.setName("Name");
        index.setValue(129.2);
        current.setIndexes(Collections.singletonList(index));
        measurement.setCurrent(current);
        return measurement;
    }
}