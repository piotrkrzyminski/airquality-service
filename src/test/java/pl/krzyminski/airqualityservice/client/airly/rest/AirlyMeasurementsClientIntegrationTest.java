package pl.krzyminski.airqualityservice.client.airly.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import pl.krzyminski.airqualityservice.client.airly.config.AirlyConfiguration;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Integration test for {@link AirlyMeasurementsClient}.
 *
 * @author Piotr Krzymiński
 */
@SpringBootTest
@EnableConfigurationProperties(AirlyConfiguration.class)
class AirlyMeasurementsClientIntegrationTest {

    @Autowired
    private AirlyMeasurementsClient airlyMeasurementsClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private boolean initialized = false;

    @BeforeEach
    public void setUp() {
        if (!initialized) {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            initialized = true;
        }
    }

    @Test
    public void testGetMeasurementForInstallationNotFound() {
        int installationId = 200;

        mockServer.expect(requestTo("https://airapi.airly.eu/v2/measurements/installation?installationId=" + installationId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON));

        Optional<Measurement> measurement = airlyMeasurementsClient.getMeasurementForInstallation(installationId);
        assertTrue(measurement.isEmpty());
    }

    @Test
    public void testGetMeasurementForInstallationSuccess() {
        String json = "{\n" +
                "   \"current\":{\n" +
                "      \"fromDateTime\":\"2018-08-24T08:24:48.652Z\",\n" +
                "      \"tillDateTime\":\"2018-08-24T09:24:48.652Z\",\n" +
                "      \"values\":[\n" +
                "         {\n" +
                "            \"name\":\"PM1\",\n" +
                "            \"value\":12.73\n" +
                "         },\n" +
                "         {\n" +
                "            \"name\":\"PM25\",\n" +
                "            \"value\":18.7\n" +
                "         },\n" +
                "         {\n" +
                "            \"name\":\"PM10\",\n" +
                "            \"value\":35.53\n" +
                "         },\n" +
                "         {\n" +
                "            \"name\":\"PRESSURE\",\n" +
                "            \"value\":1012.62\n" +
                "         },\n" +
                "         {\n" +
                "            \"name\":\"HUMIDITY\",\n" +
                "            \"value\":66.44\n" +
                "         },\n" +
                "         {\n" +
                "            \"name\":\"TEMPERATURE\",\n" +
                "            \"value\":24.71\n" +
                "         }\n" +
                "      ],\n" +
                "      \"indexes\":[\n" +
                "         {\n" +
                "            \"name\":\"AIRLY_CAQI\",\n" +
                "            \"value\":35.53,\n" +
                "            \"level\":\"LOW\",\n" +
                "            \"description\":\"Dobre powietrze.\",\n" +
                "            \"advice\":\"Możesz bez obaw wyjść na zewnątrz.\",\n" +
                "            \"color\":\"#D1CF1E\"\n" +
                "         }\n" +
                "      ],\n" +
                "      \"standards\":[\n" +
                "         {\n" +
                "            \"name\":\"WHO\",\n" +
                "            \"pollutant\":\"PM25\",\n" +
                "            \"limit\":25,\n" +
                "            \"percent\":74.81\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "}";

        int installationId = 200;

        mockServer.expect(requestTo("https://airapi.airly.eu/v2/measurements/installation?installationId=" + installationId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        Optional<Measurement> measurement = airlyMeasurementsClient.getMeasurementForInstallation(installationId);
        assertTrue(measurement.isPresent());
        assertNotNull(measurement.get().getCurrent());
        assertNotNull(measurement.get().getCurrent().getIndexes());
        Measurement.Current.Index index = measurement.get().getCurrent().getIndexes().get(0);
        assertNotNull(index.getAdvice());
        assertNotNull(index.getDescription());
        assertNotNull(index.getLevel());
        assertNotNull(index.getName());
        assertNotNull(index.getValue());
    }
}