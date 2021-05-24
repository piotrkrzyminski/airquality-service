package pl.krzyminski.airqualityservice.client.airly.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Measurement {

    private Current current;

    @Getter
    @Setter
    public static class Current {

        private LocalDateTime fromDateTime;
        private LocalDateTime tillDateTime;
        private List<Index> indexes;

        @Getter
        @Setter
        public static class Index {

            private String name;
            private Double value;
            private String level;
            private String description;
            private String advice;

            @Override
            public String toString() {
                return "Index{" +
                        "name='" + name + '\'' +
                        ", value=" + value +
                        ", level='" + level + '\'' +
                        ", description='" + description + '\'' +
                        ", advice='" + advice + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Current{" +
                    "fromDateTime=" + fromDateTime +
                    ", tillDateTime=" + tillDateTime +
                    ", indexes=" + indexes +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "current=" + current +
                '}';
    }
}