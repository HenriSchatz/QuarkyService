package app.quarky.quarkyservice.stacks.control.objects;

import app.quarky.quarkyservice.cards.data.documents.QuarkyCardDocument;
import app.quarky.quarkyservice.stacks.data.documents.QuarkyStackDocument;
import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import reactor.util.function.Tuple4;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public record QuarkyStackInfo(QuarkyStackDocument stackDocument,
                              List<QuarkyCardDocument> openCards, List<QuarkyCardDocument> finishedCards,
                              QuarkyUserDocument creator, Collection<QuarkyUserDocument> sharedUsers) {

    public static QuarkyStackInfo fromTuple(Tuple4<QuarkyStackDocument, ? extends Collection<QuarkyCardDocument>,
            QuarkyUserDocument, ? extends Collection<QuarkyUserDocument>> tuple) {
        return from(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4());
    }

    public static QuarkyStackInfo from(QuarkyStackDocument stackDocument,
                                       Collection<QuarkyCardDocument> cardDocuments,
                                       QuarkyUserDocument creator, Collection<QuarkyUserDocument> sharedUsers) {
        Map<Boolean, List<QuarkyCardDocument>> map = cardDocuments.stream()
                .collect(groupingBy(QuarkyCardDocument::getOpen, toList()));

        return new QuarkyStackInfo(stackDocument, map.get(true), map.get(false),
                creator, sharedUsers);
    }
}
