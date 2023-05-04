package app.quarky.quarkyservice.cards.data.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuarkyCardDocument {
    @Id
    UUID id;
    String title;
    LocalDateTime creationDate;
    QuestionAnswerPair questionAnswerPair;
    Boolean open;
}
