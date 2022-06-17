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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            Role roleAdmin = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            Role roleUser = roleRepository.save(new Role(RoleName.ROLE_USER));

            User admin = userRepository.save(new User(
                    "admin",
                    "admin",
                    "admin@gmail.com",
                    new BCryptPasswordEncoder().encode("admin"),
                    true,
                    "This is admin's bio",
                    "https://phantom-marca.unidadeditorial.es/394defd78b6b89197e24ded2918f99ec/resize/1320/f/jpg/assets/multimedia/imagenes/2022/05/17/16528247679627.jpg",
                    roleAdmin));

            User user = userRepository.save(new User(
                    "user",
                    "user",
                    "user@gmail.com",
                    new BCryptPasswordEncoder().encode("user"),
                    true,
                    "This is user's bio",
                    "https://imageio.forbes.com/specials-images/imageserve/627bd291633f3fbbcda36428/0x0.jpg?format=jpg&crop=2057,2059,x918,y85,safe&height=416&width=416&fit=bounds",
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
                    user
            ));
            Collection laptop1Collection = collectionRepository.save(new Collection(
                    "Laptop collection#1",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6ulPhovWcc1x-eitIFjqJg3EjD5vuFhErRg&usqp=CAU",
                    "Description of 1 laptop collection",
                    TopicName.LAPTOPS,
                    user
            ));
            Collection musicInstrument1Collection = collectionRepository.save(new Collection(
                    "Musical instrument collection#1",
                    "https://media.istockphoto.com/photos/instruments-in-white-wooden-background-picture-id1219335974?k=20&m=1219335974&s=612x612&w=0&h=pV-_xowqm4PXP780HBps68uRvGvAAu3tNNqUxP0PFsc=",
                    "Description of 1 musical instrument collection",
                    TopicName.MUSICAL_INSTRUMENTS,
                    user
            ));
            Collection mobilePhone1Collection = collectionRepository.save(new Collection(
                    "Mobile phone collection#1",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuZBeC2qhVlzN1nRWf-LnsfbLvRMM5hCEI4A&usqp=CAU",
                    "Description of 1 mobile phone collection",
                    TopicName.MOBILE_PHONES,
                    user
            ));
            List<Tag> tags = tagRepository.saveAll(Arrays.asList(
                    new Tag("beautiful"),
                    new Tag("best"),
                    new Tag("new")
            ));
            itemRepository.save(new Item(
                    "This is my car item",
                    tags,
                    user,
                    Arrays.asList(admin, user),
                    Collections.singletonList(car1Collection)
            ));
            itemRepository.save(new Item(
                    "This is book item",
                    tags,
                    admin,
                    Collections.singletonList(admin),
                    Collections.singletonList(book1Collection)
            ));
            itemRepository.save(new Item(
                    "This is mobile phone Item",
                    tags,
                    user,
                    Arrays.asList(admin,user),
                    Arrays.asList(mobilePhone1Collection,laptop1Collection)
            ));
            itemRepository.save(new Item(
                    "This is musical instrument Item",
                    new ArrayList<>(),
                    user,
                    Arrays.asList(admin,user),
                    Collections.singletonList(mobilePhone1Collection)
            ));

        }
    }
}
