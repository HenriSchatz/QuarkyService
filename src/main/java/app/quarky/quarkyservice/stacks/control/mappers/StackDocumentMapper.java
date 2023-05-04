package app.quarky.quarkyservice.stacks.control.mappers;

import app.quarky.quarkyservice.cards.control.objects.QuarkyCard;
import app.quarky.quarkyservice.stacks.control.objects.QuarkyStack;
import app.quarky.quarkyservice.stacks.data.documents.QuarkyStackDocument;
import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StackDocumentMapper {

    default QuarkyStack map(QuarkyStackDocument stackDocument, QuarkyUser creator,
                    List<QuarkyCard> openCards, List<QuarkyCard> finishedCards,
                    Collection<QuarkyUser> sharedUsers) {
        return QuarkyStack.builder()
                .id(stackDocument.getId())
                .name(stackDocument.getName())
                .openCards(openCards)
                .finishedCards(finishedCards)
                .color(stackDocument.getColor())
                .keyWords(stackDocument.getKeyWords())
                .creator(creator)
                .sharedUsers(sharedUsers)
                .build();
    }
}
