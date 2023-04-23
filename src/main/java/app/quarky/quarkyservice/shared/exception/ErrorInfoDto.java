package app.quarky.quarkyservice.shared.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorInfoDto {
    LocalDateTime timestamp;
    String path;
    int statusCode;
    String error;
    String description;

    public static ErrorInfoDto from(String path, Exception exception, HttpStatus status) {
        return new ErrorInfoDto(LocalDateTime.now(), path, status.value(),
                status.getReasonPhrase(), exception.getMessage());
    }

}
