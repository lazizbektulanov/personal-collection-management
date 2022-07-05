package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.payload.CustomFieldDto;
import uz.itransition.personalcollectionmanagement.projection.customfield.CustomFieldProjection;
import uz.itransition.personalcollectionmanagement.repository.CustomFieldRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomFieldService {

    private final CustomFieldRepository customFieldRepository;

    public void saveCollectionCustomFields(List<CustomFieldDto> customFieldDtos, Collection savedCollection) {
        List<CustomField> customFields = new ArrayList<>();
        for (CustomFieldDto customFieldDto : customFieldDtos) {
            customFields.add(new CustomField(
                    customFieldDto.getFieldId().length() != 0 ?
                            UUID.fromString(customFieldDto.getFieldId()) : null,
                    customFieldDto.getFieldName().trim(),
                    customFieldDto.getFieldType(),
                    savedCollection
            ));
        }
        customFieldRepository.saveAll(customFields);
    }

    public List<CustomFieldProjection> getCollectionCustomFields(UUID collectionId) {
        return customFieldRepository.getCustomFields(collectionId);
    }

    public List<CustomField> getCollectionAllCustomFields(UUID collectionId) {
        return customFieldRepository.findAllByCollectionId(collectionId);
    }

    public void deleteCustomField(UUID fieldId) {
        customFieldRepository.deleteById(fieldId);
    }
}
