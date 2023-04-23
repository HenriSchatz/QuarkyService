package app.quarky.quarkyservice.users.data.repositories;

import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<QuarkyUserDocument, UUID> {

    Mono<QuarkyUserDocument> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByTag(Integer tag);
}
