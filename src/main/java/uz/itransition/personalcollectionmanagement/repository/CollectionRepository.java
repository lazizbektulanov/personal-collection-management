package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.projection.CollectionProjection;
import uz.itransition.personalcollectionmanagement.projection.ItemCollectionsProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {

    @Query(nativeQuery = true,
    value = "select " +
            "cast(c.id as varchar) as id," +
            "c.title as title " +
            "from collections c " +
            "join items i on c.id = i.collection_id " +
            "where i.id=:itemId")
    List<ItemCollectionsProjection> getCollectionsByItemId(UUID itemId);

    @Query(nativeQuery = true,
    value = "select\n" +
            "cast(c.id as varchar) as collectionId,\n" +
            "       c.title as collectionTitle,\n" +
            "       c.img_url as collectionImgUrl,\n" +
            "       c.topic_name as collectionTopic,\n" +
            "       cast(c.owner_id as varchar) as collectionAuthorId,\n" +
            "       (select u.full_name as collectionAuthorName from users u\n" +
            "           where u.id=c.owner_id)\n" +
            "from collections c\n" +
            "where c.id in (select i.collection_id from items i\n" +
            "    group by i.collection_id order by count(i.id) desc limit 4)")
    List<CollectionProjection> getLargestCollections();
}
