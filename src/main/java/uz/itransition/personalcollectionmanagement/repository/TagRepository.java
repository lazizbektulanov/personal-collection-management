package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.tag.TagProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {


    @Query(nativeQuery = true,
    value = "select " +
            "cast(t.id as varchar) as id," +
            "t.name as name " +
            "from tags t " +
            "join items_tags it on t.id = it.tags_id " +
            "where it.items_id=:itemId")
    List<TagProjection> findTagsByItemId(UUID itemId);



//    List<Tag> findByNameContainingIgnoreCase(String name);
}
