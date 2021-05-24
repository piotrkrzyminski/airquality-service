package pl.krzyminski.airqualityservice.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for {@link MeasurementToAirQualityMeasurementConverter}.
 *
 * @author Piotr Krzymiński
 */
class MeasurementToAirQualityMeasurementConverterTest {

    private static final Double VALUE = 12.0;
    private static final String DESCRIPTION = "Description";
    private static final String ADVICE = "Advice";

    private final MeasurementToAirQualityMeasurementConverter converter = new MeasurementToAirQualityMeasurementConverter();

    private Measurement measurement;

    @BeforeEach
    public void setUp() {
        measurement = new Measurement();
        Measurement.Current current = new Measurement.Current();
        Measurement.Current.Index index = new Measurement.Current.Index();
        index.setValue(VALUE);
        index.setDescription(DESCRIPTION);
        index.setAdvice(ADVICE);
        current.setIndexes(Collections.singletonList(index));
        measurement.setCurrent(current);
    }

    @Test
    public void testConvert() {
        AirQualityMeasurement target = converter.convert(measurement);
        assertNotNull(target);
        assertEquals(VALUE, target.getValue(), 0.01);
        assertNotNull(DESCRIPTION, target.getDescription());
        assertNotNull(ADVICE, target.getAdvice());
    }
}