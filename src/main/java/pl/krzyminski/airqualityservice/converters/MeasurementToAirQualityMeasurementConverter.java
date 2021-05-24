package pl.krzyminski.airqualityservice.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;
import pl.krzyminski.airqualityservice.client.airly.data.Measurement;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

/**
 * Converts {@link Measurement} to {@link AirQualityMeasurement}.
 *
 * @author Piotr Krzymiński
 */
public class MeasurementToAirQualityMeasurementConverter implements Converter<Measurement, AirQualityMeasurement> {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementToAirQualityMeasurementConverter.class);

    @Override
    public AirQualityMeasurement convert(Measurement measurement) {
        LOG.debug("Attempting to convert Measurement to AirQualityMeasurement");

        if (measurement.getCurrent() == null || CollectionUtils.isEmpty(measurement.getCurrent().getIndexes())) {
            LOG.error("Missing mandatory properties for source object");
            return null;
        }

        Measurement.Current.Index index = measurement.getCurrent().getIndexes().get(0);
        AirQualityMeasurement target = new AirQualityMeasurement(index.getValue());
        target.setAdvice(index.getAdvice());
        target.setDescription(index.getDescription());

        return target;
    }
}
