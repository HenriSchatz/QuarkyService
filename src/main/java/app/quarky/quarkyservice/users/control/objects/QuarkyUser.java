package app.quarky.quarkyservice.users.control.objects;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;
import java.util.UUID;

@Value
@Builder
public class QuarkyUser {
    UUID id;
    String name;
    Integer tag;
    String email;
    String password;
    String avatarLocation;
    Collection<String> roles;
}
