package app.quarky.quarkyservice.stacks.boundary.controllers;

import app.quarky.quarkyservice.stacks.boundary.dtos.StackViewResponseDto;
import app.quarky.quarkyservice.stacks.boundary.mappers.StackDtoMapper;
import app.quarky.quarkyservice.stacks.control.services.StackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StackApiImpl implements StackApi {

    StackService stackService;
    StackDtoMapper mapper;

    @Override
    public Flux<StackViewResponseDto> getStacks(Mono<Principal> currentUser) {
        return currentUser
                .flatMapMany(principal -> stackService.getStacks(principal.getName()))
                .map(mapper::map);
    }

    @Override
    public Mono<StackViewResponseDto> getStackById(UUID stackId, Mono<Principal> currentUser) {
        return currentUser
                .flatMap(principal -> stackService.getStack(stackId, principal.getName()))
                .map(mapper::map);
    }
}
