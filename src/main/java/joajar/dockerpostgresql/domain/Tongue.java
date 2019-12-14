package joajar.dockerpostgresql.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "country_language")
public class Tongue {
    @EmbeddedId
    protected TongueId id;

    @Column(name = "is_official")
    private Boolean official;
    private Double percentage;

    public Tongue(TongueId id) {
        this.id = id;
    }
}

