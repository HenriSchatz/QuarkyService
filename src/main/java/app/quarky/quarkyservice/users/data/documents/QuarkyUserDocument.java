package app.quarky.quarkyservice.users.data.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.UUID;

@Document
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuarkyUserDocument {
    @Id
    UUID id;
    String email;
    String name;
    Integer tag;
    String password;
    String avatarLocation;
    Collection<String> roles;
}
