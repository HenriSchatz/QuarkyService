package app.quarky.quarkyservice.users.control.objects;

public class QuarkyUserExistsException extends RuntimeException {

    public QuarkyUserExistsException(String email) {
        super("User '%s' exists already".formatted(email));
    }
}
