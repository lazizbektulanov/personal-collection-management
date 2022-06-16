package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.projection.ItemProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

//    findTop4ByOrderByCreatedAtDesc
    List<Item> findTop5ByOrderByCreatedAtDesc();

    @Query(nativeQuery = true,
    value = "select " +
            "cast(i.id as varchar) as itemId," +
            "i.name as itemName," +
            "i.img_url as itemImgUrl," +
            "cast(c.id as varchar) as collectionId," +
            "c.title as collectionTitle," +
            "cast(u.id as varchar) as authorId," +
            "u.full_name  as authorName " +
            "from items i " +
            "join collections c on c.id = i.collection_id " +
            "join users u on c.owner_id = u.id " +
            "order by i.created_at desc limit 5")
    List<ItemProjection> findLatestItems();
}
