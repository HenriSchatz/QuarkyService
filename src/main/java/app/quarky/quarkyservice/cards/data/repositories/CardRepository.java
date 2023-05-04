package app.quarky.quarkyservice.cards.data.repositories;

import app.quarky.quarkyservice.cards.data.documents.QuarkyCardDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends ReactiveMongoRepository<QuarkyCardDocument, UUID> {
}
