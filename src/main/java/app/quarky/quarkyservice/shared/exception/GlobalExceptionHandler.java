package app.quarky.quarkyservice.shared.exception;

import app.quarky.quarkyservice.stacks.control.objects.StackNotFoundException;
import app.quarky.quarkyservice.users.control.objects.QuarkyUserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({QuarkyUserExistsException.class})
    Mono<ResponseEntity<ErrorInfoDto>> handleUserExists(ServerWebExchange exchange,
                                                       QuarkyUserExistsException exception) {
        return getErrorResponse(exchange, exception, BAD_REQUEST);
    }

    @ExceptionHandler(StackNotFoundException.class)
    Mono<ResponseEntity<ErrorInfoDto>> handleNotFound(ServerWebExchange exchange,
                                                      StackNotFoundException exception) {
        return getErrorResponse(exchange, exception, NOT_FOUND);
    }

    private Mono<ResponseEntity<ErrorInfoDto>> getErrorResponse(ServerWebExchange exchange,
                                                                Exception exception,
                                                                HttpStatus status) {
        var errorDto = ErrorInfoDto.from(getPath(exchange), exception, status);
        return Mono.just(ResponseEntity.status(status).body(errorDto));
    }

    private static String getPath(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().toString();
    }
}
