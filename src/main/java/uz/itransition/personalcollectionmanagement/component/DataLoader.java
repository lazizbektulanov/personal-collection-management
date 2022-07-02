package uz.itransition.personalcollectionmanagement.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uz.itransition.personalcollectionmanagement.entity.*;
import uz.itransition.personalcollectionmanagement.entity.enums.RoleName;
import uz.itransition.personalcollectionmanagement.entity.enums.TopicName;
import uz.itransition.personalcollectionmanagement.repository.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    String initMode;

    private final UserRepository userRepository;

    private final CollectionRepository collectionRepository;

    private final ItemRepository itemRepository;

    private final RoleRepository roleRepository;

    private final TagRepository tagRepository;

    private final LikeRepository likeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            Role roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            Role roleUser = roleRepository.save(new Role(RoleName.ROLE_USER));

            User admin = userRepository.save(new User(
                    "admin",
                    "admin@gmail.com",
                    new BCryptPasswordEncoder().encode("admin"),
                    true,
                    "This is admin's bio",
                    "https://phantom-marca.unidadeditorial.es/394defd78b6b89197e24ded2918f99ec/resize/1320/f/jpg/assets/multimedia/imagenes/2022/05/17/16528247679627.jpg",
                    Timestamp.from(Instant.now()),
                    roleAdmin));

            User user = userRepository.save(new User(
                    "user",
                    "user@gmail.com",
                    new BCryptPasswordEncoder().encode("user"),
                    true,
                    "This is user's bio",
                    "https://imageio.forbes.com/specials-images/imageserve/627bd291633f3fbbcda36428/0x0.jpg?format=jpg&crop=2057,2059,x918,y85,safe&height=416&width=416&fit=bounds",
                    Timestamp.from(Instant.now()),
                    roleUser)
            );
            User user2 = userRepository.save(new User(
                    "user2",
                    "user2@gmail.com",
                    new BCryptPasswordEncoder().encode("user"),
                    true,
                    "This is user's bio",
                    "https://imageio.forbes.com/specials-images/imageserve/627bd291633f3fbbcda36428/0x0.jpg?format=jpg&crop=2057,2059,x918,y85,safe&height=416&width=416&fit=bounds",
                    Timestamp.from(Instant.now()),
                    roleUser)
            );
            User user3 = userRepository.save(new User(
                    "user3",
                    "use3r@gmail.com",
                    new BCryptPasswordEncoder().encode("user"),
                    true,
                    "This is user's bio",
                    "https://imageio.forbes.com/specials-images/imageserve/627bd291633f3fbbcda36428/0x0.jpg?format=jpg&crop=2057,2059,x918,y85,safe&height=416&width=416&fit=bounds",
                    Timestamp.from(Instant.now()),
                    roleUser)
            );
            Collection book1Collection = collectionRepository.save(new Collection(
                    "Book collection#1",
                    "https://i.pinimg.com/originals/c8/b0/7e/c8b07e380215edaaa1903769586a61dc.jpg",
                    "Description of 1 book collection",
                    TopicName.BOOKS,
                    user
            ));
            Collection car1Collection = collectionRepository.save(new Collection(
                    "Car collection#1",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXYnEsK0QvigISpJ8foytvZLHW5-vYnEIJNHRBHEhhGebtQOGCXLdrIzeKIZRP8Zi8zzA&usqp=CAU",
                    "Description of 1 car collection",
                    TopicName.CARS,
                    user2
            ));
            Collection laptop1Collection = collectionRepository.save(new Collection(
                    "Laptop collection#1",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6ulPhovWcc1x-eitIFjqJg3EjD5vuFhErRg&usqp=CAU",
                    "Description of 1 laptop collection",
                    TopicName.LAPTOPS,
                    user2
            ));
            Collection musicInstrument1Collection = collectionRepository.save(new Collection(
                    "Musical instrument collection#1",
                    "https://media.istockphoto.com/photos/instruments-in-white-wooden-background-picture-id1219335974?k=20&m=1219335974&s=612x612&w=0&h=pV-_xowqm4PXP780HBps68uRvGvAAu3tNNqUxP0PFsc=",
                    "Description of 1 musical instrument collection",
                    TopicName.MUSICAL_INSTRUMENTS,
                    user2
            ));
            Collection mobilePhone1Collection = collectionRepository.save(new Collection(
                    "Mobile phone collection#1",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuZBeC2qhVlzN1nRWf-LnsfbLvRMM5hCEI4A&usqp=CAU",
                    "Description of 1 mobile phone collection",
                    TopicName.MOBILE_PHONES,
                    user2
            ));
            Tag tagBeautiful = tagRepository.save(new Tag("beautiful"));
            Tag tagBest = tagRepository.save(new Tag("best"));
            Tag tagNew = tagRepository.save(new Tag("new"));
            tagRepository.save(new Tag("books"));
            tagRepository.save(new Tag("cars"));
            tagRepository.save(new Tag("instrument"));
            tagRepository.save(new Tag("music"));
            tagRepository.save(new Tag("cinema"));
            tagRepository.save(new Tag("silverware"));
            tagRepository.save(new Tag("signs"));
            Tag oldTag = tagRepository.save(new Tag("old"));
            tagRepository.save(new Tag("fashion"));
            tagRepository.save(new Tag("picture"));
            tagRepository.save(new Tag("nature"));
            tagRepository.save(new Tag("phone"));
            tagRepository.save(new Tag("computer"));
            itemRepository.save(new Item(
                    "This is my car item",
                    Arrays.asList(tagBeautiful,tagBest,tagNew,oldTag),
                    user2,
                    car1Collection
            ));
            Item bookItem = itemRepository.save(new Item(
                    "This is book item",
                    Arrays.asList(tagBeautiful,oldTag),
                    admin,
                    book1Collection
            ));
            Item phoneItem = itemRepository.save(new Item(
                    "This is mobile phone Item",
                    Arrays.asList(tagBest, tagNew, tagBeautiful,oldTag),
                    user2,
                    mobilePhone1Collection
            ));
            Item musicalInstrItem = itemRepository.save(new Item(
                    "This is musical instrument Item",
                    new ArrayList<>(),
                    user,
                    musicInstrument1Collection
            ));
            Item phone2Item = itemRepository.save(new Item(
                    "This is mobile phone Item2",
                    Arrays.asList(tagBeautiful, tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone3Item = itemRepository.save(new Item(
                    "This is mobile phone Item3",
                    Arrays.asList(tagBeautiful, oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone4Item = itemRepository.save(new Item(
                    "This is mobile phone Item4",
                    Arrays.asList(tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone5Item = itemRepository.save(new Item(
                    "This is mobile phone Item5",
                    Arrays.asList(tagBeautiful, tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone6Item = itemRepository.save(new Item(
                    "This is mobile phone Item6",
                    Arrays.asList(tagBeautiful, oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone7Item = itemRepository.save(new Item(
                    "This is mobile phone Item7",
                    Arrays.asList(tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone8Item = itemRepository.save(new Item(
                    "This is mobile phone Item8",
                    Arrays.asList(tagBeautiful, tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone9Item = itemRepository.save(new Item(
                    "This is mobile phone Item9",
                    Arrays.asList(tagBeautiful, oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            Item phone10Item = itemRepository.save(new Item(
                    "This is mobile phone Item10",
                    Arrays.asList(tagBest,oldTag),
                    admin,
                    mobilePhone1Collection
            ));
            likeRepository.save(new Like(
                    admin,
                    phone8Item
            ));
            likeRepository.save(new Like(
                    user,
                    phone10Item
            ));
            likeRepository.save(new Like(
                    admin,
                    phone10Item
            ));
        }
    }
}
