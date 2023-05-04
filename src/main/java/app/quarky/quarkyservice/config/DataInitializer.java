package app.quarky.quarkyservice.config;

import app.quarky.quarkyservice.cards.data.documents.QuarkyCardDocument;
import app.quarky.quarkyservice.cards.data.repositories.CardRepository;
import app.quarky.quarkyservice.stacks.data.documents.QuarkyStackDocument;
import app.quarky.quarkyservice.stacks.data.repositories.StackRepository;
import app.quarky.quarkyservice.users.data.documents.QuarkyUserDocument;
import app.quarky.quarkyservice.users.data.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

@Component
@Profile("dev")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    static PodamFactory podamFactory = new PodamFactoryImpl();
    UserRepository userRepository;
    CardRepository cardRepository;
    StackRepository stackRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var sharedUsers = getSharedUsers();
        var creator = getCreator();

        userRepository.deleteAll()
                .thenMany(Flux.fromIterable(sharedUsers))
                .flatMap(userRepository::save)
                .then(Mono.just(creator))
                .flatMap(userRepository::save)
                .then(stackRepository.deleteAll())
                .thenMany(Flux.range(0, 10))
                .map(i -> getCards())
                .flatMap(c -> cardRepository.saveAll(c).collectList())
                .map(cards -> cards.stream().map(QuarkyCardDocument::getId).toList())
                .map(cardIds -> getStack(cardIds, creator.getId(), sharedUsers.stream().map(QuarkyUserDocument::getId).toList()))
                .flatMap(stackRepository::save)
                .thenMany(stackRepository.findAll())
                .doOnComplete(() -> log.info("Testnutzer angelegt: [email=jochen.kochen@gmail.com, passwort=pass]"))
                .subscribe();
    }

    private static Collection<QuarkyCardDocument> getCards() {
        var random = new SecureRandom();
        return new ConcurrentLinkedQueue<>(IntStream.range(0, 10)
                .mapToObj(i -> podamFactory.manufacturePojo(QuarkyCardDocument.class))
                .map(c -> c.setOpen(random.nextInt(2) == 0))
                .toList());
    }

    private Collection<QuarkyUserDocument> getSharedUsers() {
        List<String> userRole = List.of("USER");
        var userOne = new QuarkyUserDocument()
                .setId(UUID.randomUUID()).setName("Jonathan").setRoles(userRole)
                .setEmail("jonathan.schmonathan@jochen.de").setTag(6666)
                .setPassword(passwordEncoder.encode("houtini"))
                .setAvatarLocation("./src/jonathan");
        var userTwo = new QuarkyUserDocument()
                .setId(UUID.randomUUID()).setName("Tina").setRoles(userRole)
                .setEmail("tina.turner@lutschmir.dieTulle").setTag(7)
                .setPassword(passwordEncoder.encode("schibagwag"))
                .setAvatarLocation("./src/tina");
        var userThree = new QuarkyUserDocument()
                .setId(UUID.randomUUID()).setName("Zaza").setRoles(userRole)
                .setEmail("zaza.schmasa@zaza.zaz").setTag(124)
                .setPassword(passwordEncoder.encode("zaza"))
                .setAvatarLocation("./src/zaza");

        return List.of(userOne, userTwo, userThree);
    }

    private QuarkyUserDocument getCreator() {
        return new QuarkyUserDocument()
                .setId(UUID.randomUUID())
                .setName("Jochen")
                .setEmail("jochen.kochen@gmail.com")
                .setPassword(passwordEncoder.encode("pass"))
                .setTag(1234)
                .setAvatarLocation("./src/sebastian/nude1.png")
                .setRoles(List.of("USER"));
    }

    private static QuarkyStackDocument getStack(List<UUID> cardIds,
                                                             UUID creatorId,
                                                             List<UUID> sharedUserIds) {
        Random random = new SecureRandom();
        int size = sharedUserIds.size();
        List<UUID> newSharedUsers = new ArrayList<>();

        var amountOfSharedUsers = random.nextInt(size + 1);
        Set<Integer> addedUserIndices = new HashSet<>();
        for (int i = 0; i < amountOfSharedUsers; i++) {
            var sharedUserIndex = random.nextInt(size);
            while (addedUserIndices.contains(sharedUserIndex)) {
                sharedUserIndex = random.nextInt(size);
            }
            newSharedUsers.add(sharedUserIds.get(sharedUserIndex));
            addedUserIndices.add(sharedUserIndex);
        }

        return podamFactory.manufacturePojo(QuarkyStackDocument.class)
                .setSharedUserIds(newSharedUsers)
                .setCreatorId(creatorId)
                .setCardIds(cardIds);
    }
}
