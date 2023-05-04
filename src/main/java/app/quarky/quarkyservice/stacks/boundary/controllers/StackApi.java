package app.quarky.quarkyservice.stacks.boundary.controllers;

import app.quarky.quarkyservice.stacks.boundary.dtos.StackViewResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/stacks")
public interface StackApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Flux<StackViewResponseDto> getStacks(Mono<Principal> currentUser);

    @GetMapping(path = "/{stackId}",
            produces = APPLICATION_JSON_VALUE)
    Mono<StackViewResponseDto> getStackById(final @PathVariable("stackId") UUID stackId,
                                            Mono<Principal> currentUser);
}
