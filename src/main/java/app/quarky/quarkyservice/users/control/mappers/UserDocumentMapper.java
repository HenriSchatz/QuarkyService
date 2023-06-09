package app.quarky.quarkyservice.users.control.mappers;

import app.quarky.quarkyservice.users.control.objects.QuarkyUser;
import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import org.mapstruct.Mapper;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface UserDocumentMapper {

    QuarkyUserDocument map(QuarkyUser user);
    QuarkyUser map(QuarkyUserDocument userDocument);

    Collection<QuarkyUser> mapAll(Collection<QuarkyUserDocument> userDocuments);
}
