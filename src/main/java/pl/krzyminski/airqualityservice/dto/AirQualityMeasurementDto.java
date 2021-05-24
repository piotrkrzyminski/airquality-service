package pl.krzyminski.airqualityservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirQualityMeasurementDto {

    private final Double value;
    private String description;
    private String advice;

    public AirQualityMeasurementDto(Double value) {
        this.value = value;
    }
}
