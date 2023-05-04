package app.quarky.quarkyservice.stacks.boundary.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StackCategoryResponseDto {
    UUID id;
    String name;
    String color;
}
