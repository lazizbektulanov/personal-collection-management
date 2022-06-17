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

////    findTop4ByOrderByCreatedAtDesc
//    List<Item> findTop5ByOrderByCreatedAtDesc();

    @Query(nativeQuery = true,
    value = "select " +
            "cast(i.id as varchar) as id," +
            "i.name as itemName," +
            "i.img_url as itemImgUrl," +
            "cast(i.created_by_id as varchar) as authorId, " +
            "u.full_name as authorName," +
            "(select count(il.user_id) from items_likes il " +
            "where il.item_id=i.id) as itemLikes " +
            "from items i " +
            "join users u on i.created_by_id = u.id " +
            "order by i.created_at desc " +
            "limit 5")
    List<ItemProjection> findLatestItems();
}
