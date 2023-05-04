package app.quarky.quarkyservice.stacks.data.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuarkyStackDocument {

    @Id
    UUID id;
    String name;
    List<UUID> cardIds;
    String color;
    List<StackCategory> keyWords;
    UUID creatorId;
    List<UUID> sharedUserIds;
}
