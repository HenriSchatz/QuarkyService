package app.quarky.quarkyservice.users.boundary.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserViewResponseDto {
    String name;
    Integer tag;
    String avatarLocation;
}
