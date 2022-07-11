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
import uz.itransition.personalcollectionmanagement.projection.response.Response;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import java.io.IOException;
import java.util.*;


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

    public void saveCollection(CollectionDto collectionDto, MultipartFile collectionImage) throws IOException {
        Collection savedCollection;
        if (collectionDto.getId().length() != 0)
            savedCollection = editCollection(collectionDto, collectionImage);
        else
            savedCollection = createCollection(collectionDto, collectionImage);
        customFieldService.saveCollectionCustomFields(collectionDto.getCustomFields(), savedCollection);
    }

    private Collection createCollection(CollectionDto collectionDto, MultipartFile collectionImage) throws IOException {
        User currentUser = authService.getCurrentUser();
        return collectionRepository.save(new Collection(
                collectionDto.getTitle().trim(),
                fileService.getUrlIfNotExists(collectionImage, collectionDto.getPreviousImgUrl()),
                collectionDto.getDescription().trim(),
                TopicName.valueOf(collectionDto.getTopic()),
                currentUser
        ));
    }

    private Collection editCollection(CollectionDto collectionDto, MultipartFile collectionImage) throws IOException {
        Optional<Collection> collection =
                collectionRepository.findById(UUID.fromString(collectionDto.getId()));
        if (collection.isPresent()) {
            Collection editingCollection = collection.get();
            editingCollection.setTitle(collectionDto.getTitle().trim());
            editingCollection.setImgUrl(
                    fileService.getUrlIfNotExists(collectionImage, collectionDto.getPreviousImgUrl())
            );
            editingCollection.setDescription(collectionDto.getDescription().trim());
            editingCollection.setTopicName(TopicName.valueOf(collectionDto.getTopic()));
            return collectionRepository.save(editingCollection);
        }
        return null;
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
        Optional<Collection> collection = collectionRepository.findById(collectionId);
        if (collection.isPresent()) {
            collectionRepository.deleteById(collectionId);
        }
    }
}
