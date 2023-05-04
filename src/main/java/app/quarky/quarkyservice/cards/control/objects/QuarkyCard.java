package app.quarky.quarkyservice.cards.control.objects;

import app.quarky.quarkyservice.cards.data.documents.QuestionAnswerPair;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuarkyCard {
    UUID id;
    String title;
    LocalDateTime creationDate;
    QuestionAnswerPair questionAnswerPair;
}
