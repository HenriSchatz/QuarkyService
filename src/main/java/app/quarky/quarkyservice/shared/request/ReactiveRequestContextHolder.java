package app.quarky.quarkyservice.shared.request;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class ReactiveRequestContextHolder {

    public static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;

    private ReactiveRequestContextHolder() {
        // make non-instantiable
    }

    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(
                context -> Mono.just(context.get(CONTEXT_KEY))
        );
    }
}
