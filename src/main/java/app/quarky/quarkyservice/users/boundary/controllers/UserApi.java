package app.quarky.quarkyservice.users.boundary.controllers;

import app.quarky.quarkyservice.users.boundary.dtos.UserCreationRequestDto;
import app.quarky.quarkyservice.users.boundary.dtos.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/users")
public interface UserApi {

    @PostMapping(path = "/register",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<UserResponseDto>> registerUser(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Flux<UserResponseDto> getUsers();

    @GetMapping(path = "/current",
            produces = APPLICATION_JSON_VALUE)
    Mono<UserResponseDto> getCurrentUser(Mono<Principal> principal);
}
