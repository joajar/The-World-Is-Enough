package joajar.dockerpostgresql.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
class ErrorResponse {
    private HttpStatus status;
    private String message;
}
