package app.quarky.quarkyservice.stacks.data.repositories;

import app.quarky.quarkyservice.stacks.data.documents.QuarkyStackDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface StackRepository extends ReactiveMongoRepository<QuarkyStackDocument, UUID> {
    Flux<QuarkyStackDocument> findAllByCreatorId(UUID creatorId);
}
