package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.CustomField;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField, UUID> {

    List<CustomField> findByCollectionId(UUID collection_id);
}
