package joajar.dockerpostgresql.domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "country")
public class Country {
    @Id
    private String code;//!!!
    private String name;//!!!
    private String continent;//!!!
    private String region;
    private Double surface_area;
    private Integer indep_year;
    private Integer population;//!!!

    @Column(name = "life_expectancy")
    private Double expectancy;//!!!

    private Double gnp;
    private Double gnp_old;
    private String local_name;
    private String government_form;
    private String head_of_state;
    private Integer capital;
    private String code2;
}

