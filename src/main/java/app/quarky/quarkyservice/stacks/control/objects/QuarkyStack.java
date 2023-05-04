package app.quarky.quarkyservice.stacks.control.objects;

import app.quarky.quarkyservice.cards.control.objects.QuarkyCard;
import app.quarky.quarkyservice.stacks.data.documents.StackCategory;
import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class QuarkyStack {
    UUID id;
    String name;
    List<QuarkyCard> openCards;
    List<QuarkyCard> finishedCards;
    String color;
    List<StackCategory> keyWords;
    QuarkyUser creator;
    Collection<QuarkyUser> sharedUsers;
}
