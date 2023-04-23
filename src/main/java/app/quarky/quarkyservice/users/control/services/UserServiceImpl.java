package app.quarky.quarkyservice.users.control.services;

import app.quarky.quarkyservice.users.control.mappers.UserDocumentMapper;
import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import app.quarky.quarkyservice.users.control.objects.QuarkyUserExistsException;
import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import app.quarky.quarkyservice.users.data.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserDocumentMapper mapper;
    Random random;

    private static UserDetails getUserDetails(QuarkyUserDocument userDocument) {
        return User.withUsername(userDocument.getEmail())
                .password(userDocument.getPassword())
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .roles(getRoles(userDocument).toArray(new String[0])).build();
    }

    private static Collection<String> getRoles(QuarkyUserDocument userDocument) {
        var roles = userDocument.getRoles();

        return roles == null ? Collections.emptyList() : roles;
    }

    private static QuarkyUserDocument addIdAndRoles(QuarkyUserDocument userDocument) {
        return userDocument.setId(UUID.randomUUID()).setRoles(List.of("USER"));
    }

    private Mono<QuarkyUserDocument> addAdditionalInformation(QuarkyUserDocument userDocument) {
        return addTag(userDocument)
                .map(UserServiceImpl::addIdAndRoles);
    }


    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("user %s not found".formatted(email))))
                .map(UserServiceImpl::getUserDetails);
    }

    @Override
    public Mono<QuarkyUser> createUser(QuarkyUser user) {
        var email = user.getEmail();
        return userRepository.existsByEmail(email)
                .flatMap(exists -> Boolean.TRUE.equals(exists) ?
                        Mono.error(new QuarkyUserExistsException(email)) :
                        addAdditionalInformation(mapper.map(user)))
                .flatMap(userRepository::save)
                .map(mapper::map);
    }

    @Override
    public Flux<QuarkyUser> getUsers() {
        return userRepository.findAll()
                .map(mapper::map);
    }

    @Override
    public Mono<QuarkyUser> getUser(String email) {
        return userRepository.findByEmail(email)
                .map(mapper::map);
    }

    private Mono<QuarkyUserDocument> addTag(QuarkyUserDocument userDocument) {
        return getNewTag()
                .map(userDocument::setTag);
    }

    private Mono<Integer> getNewTag() {
        var maxTag = 9999;
        return Mono.fromSupplier(() -> random.nextInt(maxTag + 1))
                .flatMap(tag -> userRepository.existsByTag(tag)
                        .flatMap(exists -> Boolean.TRUE.equals(exists) ? getNewTag() : Mono.just(tag)));
    }
}
