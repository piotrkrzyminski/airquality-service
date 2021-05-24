package pl.krzyminski.airqualityservice.converters;

import org.springframework.core.convert.converter.Converter;
import pl.krzyminski.airqualityservice.dto.AirQualityMeasurementDto;
import pl.krzyminski.airqualityservice.models.AirQualityMeasurement;

/**
 * Converts {@link AirQualityMeasurement} to {@link AirQualityMeasurementDto}.
 *
 * @author Piotr Krzymiński
 */
public class AirQualityMeasurementDtoConverter implements Converter<AirQualityMeasurement, AirQualityMeasurementDto> {

    @Override
    public AirQualityMeasurementDto convert(AirQualityMeasurement source) {
        AirQualityMeasurementDto target = new AirQualityMeasurementDto(source.getValue());
        target.setAdvice(source.getAdvice());
        target.setDescription(source.getDescription());

        return target;
    }
}
