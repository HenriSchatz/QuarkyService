package app.quarky.quarkyservice.stacks.control.objects;

import java.util.UUID;

public class StackNotFoundException extends RuntimeException {

    public StackNotFoundException(UUID id) {
        super("Stack with id '%s' not found".formatted(id));
    }
}
