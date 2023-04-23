package app.quarky.quarkyservice.users.boundary.mappers;

import app.quarky.quarkyservice.users.boundary.dtos.UserCreationRequestDto;
import app.quarky.quarkyservice.users.boundary.dtos.UserResponseDto;
import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tag", ignore = true)
    @Mapping(target = "roles", ignore = true)
    QuarkyUser map(UserCreationRequestDto userCreationRequestDto);

    UserResponseDto map(QuarkyUser quarkyUser);
}
