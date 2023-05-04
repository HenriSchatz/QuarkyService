package app.quarky.quarkyservice.stacks.data.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StackCategory {
    UUID id;
    String name;
    String color;
}
