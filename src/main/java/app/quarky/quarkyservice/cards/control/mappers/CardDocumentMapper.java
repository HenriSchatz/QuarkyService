package app.quarky.quarkyservice.cards.control.mappers;

import app.quarky.quarkyservice.cards.control.objects.QuarkyCard;
import app.quarky.quarkyservice.cards.data.documents.QuarkyCardDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardDocumentMapper {
    List<QuarkyCard> mapAll(List<QuarkyCardDocument> cardDocuments);
}
