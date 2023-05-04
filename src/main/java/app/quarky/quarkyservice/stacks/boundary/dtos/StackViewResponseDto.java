package app.quarky.quarkyservice.stacks.boundary.dtos;

import app.quarky.quarkyservice.users.boundary.dtos.UserViewResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StackViewResponseDto {
    UUID id;
    String name;
    Integer openCardCount;
    Integer finishedCardCount;
    String color;
    List<StackCategoryResponseDto> keyWords;
    UserViewResponseDto creator;
    Collection<UserViewResponseDto> sharedUsers;
}
