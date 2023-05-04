package app.quarky.quarkyservice.cards.data.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionAnswerPair {
    String question;
    String answer;
}
