package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.CustomFieldValue;
import uz.itransition.personalcollectionmanagement.projection.customfield.CustomFieldValueProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomFieldValueRepository extends JpaRepository<CustomFieldValue, UUID> {


    @Query(nativeQuery = true,
            value = "select " +
                    "cast(cfv.id as varchar) as id," +
                    "cfv.field_value as fieldValue " +
                    "from custom_field_values cfv " +
                    "join custom_fields cf on cf.id = cfv.custom_field_id " +
                    "where cfv.item_id=:itemId " +
                    "and (cf.field_type='text' or cf.field_type='date') " +
                    "order by cfv.created_at") //checking
    List<CustomFieldValueProjection> getCustomFieldValues(UUID itemId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(cfv.id as varchar) as id," +
                    "cf.field_name as fieldName," +
                    "cf.field_type as fieldType," +
                    "cfv.field_value as fieldValue " +
                    "from custom_field_values cfv " +
                    "full join custom_fields cf on cf.id = cfv.custom_field_id " +
                    "where cfv.item_id=:itemId")
    List<CustomFieldValueProjection> getItemCustomFieldValues(UUID itemId);

    List<CustomFieldValue> findByItemId(UUID item_id);
}
