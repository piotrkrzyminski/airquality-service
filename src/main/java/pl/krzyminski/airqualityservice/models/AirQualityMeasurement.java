package pl.krzyminski.airqualityservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirQualityMeasurement {

    private final Double value;
    private String description;
    private String advice;

    public AirQualityMeasurement(Double value) {
        this.value = value;
    }
}
