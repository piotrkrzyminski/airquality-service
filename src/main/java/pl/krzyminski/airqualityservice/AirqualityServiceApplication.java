package pl.krzyminski.airqualityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.convert.converter.ConverterRegistry;
import pl.krzyminski.airqualityservice.converters.MeasurementToAirQualityMeasurementConverter;
import pl.krzyminski.airqualityservice.converters.AirQualityMeasurementDtoConverter;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan("pl.krzyminski.airqualityservice.client.airly.config")
public class AirqualityServiceApplication {

    private final ConverterRegistry converterRegistry;

    public AirqualityServiceApplication(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(AirqualityServiceApplication.class, args);
    }

    @PostConstruct
    public void configureConverter() {
        converterRegistry.addConverter(new MeasurementToAirQualityMeasurementConverter());
        converterRegistry.addConverter(new AirQualityMeasurementDtoConverter());
    }

}
