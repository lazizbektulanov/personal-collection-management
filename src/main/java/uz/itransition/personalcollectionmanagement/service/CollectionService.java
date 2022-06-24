package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.entity.enums.CustomFieldType;
import uz.itransition.personalcollectionmanagement.entity.enums.RoleName;
import uz.itransition.personalcollectionmanagement.entity.enums.TopicName;
import uz.itransition.personalcollectionmanagement.payload.CollectionDto;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.repository.CustomFieldRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    private final AuthService authService;

    private final CustomFieldRepository customFieldRepository;


    public List<CollectionProjection> getLargestCollections() {
        return collectionRepository.getLargestCollections();
    }

    public CollectionByIdProjection getCollectionById(UUID collectionId) {
        return collectionRepository.getCollectionById(collectionId);
    }

    public void createCollection(CollectionDto collectionDto) {
        User currentUser = authService.getCurrentUser();
        Collection savedCollection = collectionRepository.save(new Collection(
                collectionDto.getTitle().trim(),
                collectionDto.getDescription().trim(),
                TopicName.valueOf(collectionDto.getTopic()),
                currentUser
        ));
        saveCollectionCustomFields(collectionDto.getCustomFields(), savedCollection);
    }

    private void saveCollectionCustomFields(JSONObject customFieldJson, Collection savedCollection) {
        List<CustomField> customFields = new ArrayList<>();
        for (String key : customFieldJson.keySet()) {
            Object value = customFieldJson.get(key);
            System.out.println(value.getClass().getTypeName());
            customFields.add(new CustomField(
//                    key.trim(),
//                    String.valueOf(value).trim(),
//                    getValueType(value),
//                    savedCollection
            ));
        }
        customFieldRepository.saveAll(customFields);
    }

    public CustomFieldType getValueType(Object value) {
        if (value.getClass().getSimpleName().equals("Boolean")) {
            return CustomFieldType.BOOLEAN;
        } else {
            try {
                Date.valueOf(String.valueOf(value));
                return CustomFieldType.DATE;
            } catch (Exception e) {
                return CustomFieldType.STRING;
            }
        }
    }

    public boolean checkCollectionOwner(UUID collectionId) {
        User currentUser = authService.getCurrentUser();
        return currentUser.getRole().getRoleName().equals(RoleName.ROLE_ADMIN) ||
                collectionRepository.existsByIdAndOwnerId(collectionId, currentUser.getId());
    }
}
