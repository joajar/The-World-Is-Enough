package joajar.dockerpostgresql.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Embeddable
public class TongueId implements Serializable {
    protected String language;//--!!!
    @Column(name = "country_code")
    protected String code;
}
