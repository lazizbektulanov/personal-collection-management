package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.projection.CustomFieldProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField, UUID> {

    List<CustomField> findByCollectionId(UUID collection_id);

    @Query(nativeQuery = true,
    value = "select " +
            "cast(cf.id as varchar) as id," +
            "cf.field_name as fieldName " +
            "from custom_fields cf " +
            "where cf.collection_id=:collectionId " +
            "and (cf.field_type='text' or cf.field_type='date')")
    List<CustomFieldProjection> getCustomFields(UUID collectionId);

    List<CustomField> findAllByCollectionId(UUID collection_id);

}
