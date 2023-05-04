package app.quarky.quarkyservice.stacks.control.services;

import app.quarky.quarkyservice.stacks.control.objects.QuarkyStack;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StackService {
    Flux<QuarkyStack> getStacks(String email);

    Mono<QuarkyStack> getStack(UUID stackId, String email);
}
