package pl.krzyminski.airqualityservice.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link AirQualityMeasurementDtoConverter}.
 *
 * @author Piotr Krzymiński
 */
class AirQualityMeasurementDtoConverterTest {

    private static final Double VALUE = 10.0;
    private static final String ADVICE = "Dummy advice";
    private static final String DESCRIPTION = "Dummy description";

    private final AirQualityMeasurementDtoConverter airQualityMeasurementDtoConverter =
            new AirQualityMeasurementDtoConverter();

    private AirQualityMeasurement source;

    @BeforeEach
    public void setUp() {
        source = new AirQualityMeasurement(VALUE);
        source.setDescription(DESCRIPTION);
        source.setAdvice(ADVICE);
    }

    @Test
    public void convert() {
        AirQualityMeasurementDto target = airQualityMeasurementDtoConverter.convert(source);

        assertNotNull(target);
        assertEquals(VALUE, target.getValue(), 0.01);
        assertEquals(DESCRIPTION, target.getDescription());
        assertEquals(ADVICE, target.getAdvice());
    }
}