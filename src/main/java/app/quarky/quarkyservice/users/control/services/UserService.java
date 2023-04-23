package app.quarky.quarkyservice.users.control.services;

import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<QuarkyUser> createUser(QuarkyUser user);
    Flux<QuarkyUser> getUsers();

    Mono<QuarkyUser> getUser(String email);
}
