package app.quarky.quarkyservice.users.boundary.controllers;

import app.quarky.quarkyservice.shared.request.ReactiveRequestContextHolder;
import app.quarky.quarkyservice.users.boundary.dtos.UserCreationRequestDto;
import app.quarky.quarkyservice.users.boundary.dtos.UserResponseDto;
import app.quarky.quarkyservice.users.boundary.mappers.UserDtoMapper;
import app.quarky.quarkyservice.users.control.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApiImpl implements UserApi {

    UserService userService;
    UserDtoMapper mapper;

    @Override
    public Mono<ResponseEntity<UserResponseDto>> registerUser(UserCreationRequestDto userCreationRequestDto) {

        return Mono.just(userCreationRequestDto)
                .map(mapper::map)
                .flatMap(userService::createUser)
                .map(u -> ResponseEntity.created(URI.create("/users/current")).body(mapper.map(u)));
    }

    @Override
    public Flux<UserResponseDto> getUsers() {
        return userService.getUsers()
                .map(mapper::map);
    }

    @Override
    public Mono<UserResponseDto> getCurrentUser(Mono<Principal> principal) {
        return principal
                .flatMap(p -> userService.getUser(p.getName()))
                .map(mapper::map);
    }

    @GetMapping("/request")
    public Mono<Map<String, Object>> getRequest() {
        return ReactiveRequestContextHolder.getRequest()
                .map(r ->  {
                    Map<String, Object> map = new ConcurrentHashMap<>();
                    map.put("id", r.getId());
                    map.put("method", r.getMethod().name());
                    map.put("cookies", r.getCookies());
                    map.put("remoteAddress", r.getRemoteAddress().toString());
                    map.put("headers", r.getHeaders());
                    return map;
                });
    }
}
