package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.entity.enums.RoleName;
import uz.itransition.personalcollectionmanagement.entity.enums.TopicName;
import uz.itransition.personalcollectionmanagement.payload.CollectionDto;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import java.io.IOException;
import java.util.*;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_COLLECTION_IMG_URL;


@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    private final AuthService authService;

    private final CustomFieldService customFieldService;

    private final FileService fileService;

    public List<CollectionProjection> getLargestCollections() {
        return collectionRepository.getLargestCollections();
    }

    public CollectionByIdProjection getCollectionById(UUID collectionId) {
        return collectionRepository.getCollectionById(collectionId);
    }

    public void createCollection(CollectionDto collectionDto, MultipartFile collectionImage) throws IOException {
        System.out.println(collectionDto.getCustomFields());
        User currentUser = authService.getCurrentUser();
        Collection savedCollection = collectionRepository.save(new Collection(
                collectionDto.getId().length() != 0 ? UUID.fromString(collectionDto.getId()) : null,
                collectionDto.getTitle().trim(),
                collectionImage != null ? fileService.uploadFile(collectionImage) :
                        DEFAULT_COLLECTION_IMG_URL,
                collectionDto.getDescription().trim(),
                TopicName.valueOf(collectionDto.getTopic()),
                currentUser
        ));
        customFieldService.saveCollectionCustomFields(collectionDto.getCustomFields(), savedCollection);
    }

    public boolean isCollectionOwner(UUID collectionId) {
        User currentUser = authService.getCurrentUser();
        if (currentUser.getId() == null) return false;
        return currentUser.getRole().getRoleName().equals(RoleName.ROLE_ADMIN) ||
                collectionRepository.existsByIdAndOwnerId(collectionId, currentUser.getId());
    }

    public List<String> getCollectionTopics() {
        List<String> topics = new ArrayList<>();
        for (TopicName value : TopicName.values()) {
            topics.add(value.toString());
        }
        return topics;
    }

    public Page<CollectionProjection> getAllCollections(Integer page) {
        int pageSize = Integer.parseInt(Constants.DEFAULT_PAGE_SIZE_GET_ALL);
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return collectionRepository.getAllCollections(pageable);
    }

    public void deleteCollection(UUID collectionId) {
        collectionRepository.deleteById(collectionId);
    }
}
