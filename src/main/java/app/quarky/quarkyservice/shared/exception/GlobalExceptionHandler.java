package app.quarky.quarkyservice.shared.exception;

import app.quarky.quarkyservice.users.control.objects.QuarkyUserExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuarkyUserExistsException.class)
    Mono<ResponseEntity<ErrorInfoDto>> handleUserExists(ServerWebExchange exchange,
                                                       QuarkyUserExistsException exception) {
        var path = exchange.getRequest().getPath().toString();

        return Mono.just(ResponseEntity.status(BAD_REQUEST).body(ErrorInfoDto.from(path, exception, BAD_REQUEST)));
    }
}
