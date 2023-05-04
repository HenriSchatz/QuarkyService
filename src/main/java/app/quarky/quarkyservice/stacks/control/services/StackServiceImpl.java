package app.quarky.quarkyservice.stacks.control.services;

import app.quarky.quarkyservice.cards.control.mappers.CardDocumentMapper;
import app.quarky.quarkyservice.cards.data.repositories.CardRepository;
import app.quarky.quarkyservice.stacks.control.mappers.StackDocumentMapper;
import app.quarky.quarkyservice.stacks.control.objects.QuarkyStack;
import app.quarky.quarkyservice.stacks.control.objects.QuarkyStackInfo;
import app.quarky.quarkyservice.stacks.control.objects.StackNotFoundException;
import app.quarky.quarkyservice.stacks.data.documents.QuarkyStackDocument;
import app.quarky.quarkyservice.stacks.data.repositories.StackRepository;
import app.quarky.quarkyservice.users.control.mappers.UserDocumentMapper;
import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import app.quarky.quarkyservice.users.data.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StackServiceImpl implements StackService {

    StackRepository stackRepository;
    CardRepository cardRepository;
    UserRepository userRepository;
    StackDocumentMapper stackMapper;
    CardDocumentMapper cardMapper;
    UserDocumentMapper userMapper;

    @Override
    public Flux<QuarkyStack> getStacks(String email) {
        return userRepository.findByEmail(email)
                .flatMapMany(this::getStacksByCreator)
                .flatMap(this::getStackInfo)
                .map(this::toStack);
    }

    @Override
    public Mono<QuarkyStack> getStack(UUID stackId, String email) {
        return stackRepository.findById(stackId)
                .switchIfEmpty(Mono.error(new StackNotFoundException(stackId)))
                .flatMap(stack -> userRepository.findById(stack.getCreatorId())
                        .flatMap(creator -> Objects.equals(creator.getEmail(), email) ? Mono.just(stack) : Mono.error(new StackNotFoundException(stackId))))
                .flatMap(this::getStackInfo)
                .map(this::toStack);
    }

    private Flux<QuarkyStackDocument> getStacksByCreator(QuarkyUserDocument creator) {
        return stackRepository.findAllByCreatorId(creator.getId());
    }

    private Mono<QuarkyStackInfo> getStackInfo(QuarkyStackDocument stackDocument) {
        return Mono.zip(
                Mono.just(stackDocument),
                cardRepository.findAllById(stackDocument.getCardIds()).collectList(),
                userRepository.findById(stackDocument.getCreatorId()),
                userRepository.findAllById(stackDocument.getSharedUserIds()).collectList()
        ).map(QuarkyStackInfo::fromTuple);
    }

    private QuarkyStack toStack(QuarkyStackInfo quarkyStackInfo) {
        var creator = userMapper.map(quarkyStackInfo.creator());
        var openCards = cardMapper.mapAll(quarkyStackInfo.openCards());
        var finishedCards = cardMapper.mapAll(quarkyStackInfo.finishedCards());
        var sharedUsers = userMapper.mapAll(quarkyStackInfo.sharedUsers());

        return stackMapper.map(quarkyStackInfo.stackDocument(), creator,
                openCards, finishedCards, sharedUsers);
    }
}
