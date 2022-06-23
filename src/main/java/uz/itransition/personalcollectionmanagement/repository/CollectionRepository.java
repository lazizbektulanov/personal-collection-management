package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {

    @Query(nativeQuery = true,
            value = "select\n" +
                    "cast(c.id as varchar) as id,\n" +
                    "       c.title as title,\n" +
                    "       c.img_url as imgUrl,\n" +
                    "       c.topic_name as topic,\n" +
                    "       cast(c.owner_id as varchar) as authorId,\n" +
                    "       (select u.full_name as authorName from users u\n" +
                    "           where u.id=c.owner_id),\n" +
                    "       (select u.profile_img_url as authorProfileImgUrl from users u\n" +
                    "       where u.id=c.owner_id)" +
                    "from collections c\n" +
                    "where c.id in (select i.collection_id from items i\n" +
                    "    group by i.collection_id order by count(i.id) desc limit 4)")
    List<CollectionProjection> getLargestCollections();

    @Query(nativeQuery = true,
            value = "select\n" +
                    "cast(c.id as varchar) as id,\n" +
                    "       c.title as title,\n" +
                    "       c.img_url as imgUrl,\n" +
                    "       c.topic_name as topic,\n" +
                    "       cast(c.owner_id as varchar) as authorId,\n" +
                    "       (select u.full_name as authorName from users u\n" +
                    "           where u.id=c.owner_id),\n" +
                    "       (select u.profile_img_url as authorProfileImgUrl from users u\n" +
                    "       where u.id=c.owner_id)" +
                    "from collections c\n" +
                    "where c.owner_id=:userId")
    List<CollectionProjection> getUserCollections(UUID userId);

    boolean existsByIdAndOwnerId(UUID id, UUID owner_id);

    @Query(nativeQuery = true,
    value = "select " +
            "cast(c.id as varchar) as id," +
            "c.title as title," +
            "c.description as description," +
            "c.img_url as imgUrl," +
            "c.topic_name as topicName," +
            "c.created_at as createdAt " +
            "from collections c " +
            "where c.id=:collectionId")
    CollectionByIdProjection getCollectionById(UUID collectionId);
}
