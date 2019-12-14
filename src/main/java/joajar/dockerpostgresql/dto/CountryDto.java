package joajar.dockerpostgresql.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CountryDto {
    @JsonIgnore
    private String code;

    private String name;

    private String continent;

    private Integer population;

    private String life_expectancy;

    private String country_language;
}
