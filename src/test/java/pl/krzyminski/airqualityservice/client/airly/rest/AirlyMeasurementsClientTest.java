package pl.krzyminski.airqualityservice.client.airly.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import pl.krzyminski.airqualityservice.client.airly.config.AirlyConfiguration;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link AirlyMeasurementsClient}.
 *
 * @author Piotr Krzymiński
 */
@ExtendWith(MockitoExtension.class)
class AirlyMeasurementsClientTest {

    @InjectMocks
    private AirlyMeasurementsClient airlyMeasurementsClient;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private AirlyConfiguration airlyConfiguration;

    @BeforeEach
    public void setUp() {
        Mockito.doReturn("https://airapi.airly.eu/v2")
                .when(airlyConfiguration)
                .getServer();
        Mockito.doReturn("/measurements")
                .when(airlyConfiguration)
                .getMeasurement();
    }

    @Test
    public void testGetMeasurementForInstallationFailed() {
        Mockito.doThrow(RestClientResponseException.class)
                .when(restTemplate)
                .exchange(Mockito.any(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
                        Mockito.eq(Measurement.class));

        assertTrue(airlyMeasurementsClient.getMeasurementForInstallation(104).isEmpty());
    }

    @Test
    public void testGetMeasurementForInstallationSuccess() {
        Measurement mockMeasurement = Mockito.mock(Measurement.class);

        Mockito.doReturn(ResponseEntity.ok(mockMeasurement))
                .when(restTemplate)
                .exchange(Mockito.any(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
                        Mockito.eq(Measurement.class));

        assertTrue(airlyMeasurementsClient.getMeasurementForInstallation(104).isPresent());
    }
}