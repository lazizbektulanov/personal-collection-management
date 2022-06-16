package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.ItemCollection;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCollectionRepository extends JpaRepository<ItemCollection, UUID> {



}
