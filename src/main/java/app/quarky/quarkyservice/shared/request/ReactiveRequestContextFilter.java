package app.quarky.quarkyservice.shared.request;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
class ReactiveRequestContextFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        return chain.filter(exchange)
                .contextWrite(context -> context.put(ReactiveRequestContextHolder.CONTEXT_KEY, request));
    }
}
