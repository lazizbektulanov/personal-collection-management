package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.projection.CustomFieldProjection;
import uz.itransition.personalcollectionmanagement.repository.CustomFieldRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomFieldService {

    private final CustomFieldRepository customFieldRepository;

    public void saveCollectionCustomFields(JSONObject customFieldJson, Collection savedCollection) {
        List<CustomField> customFields = new ArrayList<>();
        for (String key : customFieldJson.keySet()) {
            Object value = customFieldJson.get(key);
            System.out.println(value.getClass().getTypeName());
            customFields.add(new CustomField(
                    key.trim(),
                    String.valueOf(value).trim(),
                    savedCollection
            ));
        }
        customFieldRepository.saveAll(customFields);
    }

    public List<CustomFieldProjection> getCollectionCustomFields(UUID collectionId) {
        return customFieldRepository.getCustomFields(collectionId);
    }
}
