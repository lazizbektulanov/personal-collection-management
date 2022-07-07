package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.thymeleaf.processor.templateboundaries.ITemplateBoundariesProcessor;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {


    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId, " +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join collections c on c.id = i.collection_id " +
                    "order by i.created_at desc " +
                    "limit 5 ")
    List<ItemProjection> findLatestItems();

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId, " +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl," +
                    "(select exists(select l.id from likes l " +
                    "where l.item_id=i.id and l.user_id=:userId)) as isLiked " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join collections c on c.id = i.collection_id " +
                    "order by i.created_at desc " +
                    "limit 5 ")
    List<ItemProjection> findLatestItems(UUID userId);


    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "(select c.title as itemCollectionTitle from collections c " +
                    "where c.id=i.collection_id)," +
                    "cast(i.created_by_id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl, " +
                    "(select count(c.id) from comments c " +
                    "where c.item_id=i.id) as itemCommentsNumber," +
                    "(select count(l.id) from likes l " +
                    "where l.item_id=i.id) as itemLikes " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "where i.id=:itemId")
    ItemByIdProjection getItemById(UUID itemId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "(select c.title as itemCollectionTitle from collections c " +
                    "where c.id=i.collection_id)," +
                    "cast(i.created_by_id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl, " +
                    "(select count(c.id) from comments c " +
                    "where c.item_id=i.id) as itemCommentsNumber," +
                    "(select count(l.id) from likes l " +
                    "where l.item_id=i.id) as itemLikes, " +
                    "exists(select l.id from likes l " +
                    "where l.item_id=i.id and l.user_id=:userId) as isLiked " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "where i.id=:itemId")
    ItemByIdProjection getItemById(UUID itemId, UUID userId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "cast(i.collection_id as varchar) as itemCollectionId " +
                    "from items i " +
                    "where i.id=:itemId")
    ItemByIdProjection getEditingItem(UUID itemId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as name," +
                    "i.created_at as createdAt " +
                    "from items i " +
                    "where i.collection_id=:collectionId")
    Page<CollectionItemsProjection> getItemsByCollectionId(Pageable pageable, UUID collectionId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as name," +
                    "i.created_at as createdAt, " +
                    "exists(select l.id from likes l " +
                    "where l.item_id=i.id and l.user_id=:userId) as isLiked " +
                    "from items i " +
                    "where i.collection_id=:collectionId",
            countQuery = "select count(*) from items ")
    Page<CollectionItemsProjection> getItemsByCollectionId(Pageable pageable, UUID collectionId, UUID userId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join items_tags it on i.id = it.items_id " +
                    "join collections c on c.id = i.collection_id " +
                    "where it.tags_id=:tagId")
    Page<ItemProjection> getItemsByTag(UUID tagId, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl, " +
                    "exists(select l.id from likes l " +
                    "where l.item_id=i.id and l.user_id=:userId) as isLiked " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join items_tags it on i.id = it.items_id " +
                    "join collections c on c.id = i.collection_id " +
                    "where it.tags_id=:tagId",
            countQuery = "select count(*) from items " +
                    "join items_tags it on items.id = it.items_id " +
                    "where it.tags_id=:tagId")
    Page<ItemProjection> getItemsByTag(UUID tagId, Pageable pageable, UUID userId);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join collections c on c.id = i.collection_id ")
    Page<ItemProjection> getAllItems(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select " +
                    "cast(i.id as varchar) as id," +
                    "i.name as itemName," +
                    "i.img_url as itemImgUrl," +
                    "cast(i.collection_id as varchar) as itemCollectionId," +
                    "c.title as itemCollectionTitle," +
                    "cast(u.id as varchar) as authorId," +
                    "u.full_name as authorName," +
                    "u.profile_img_url as authorProfileImgUrl, " +
                    "exists(select l.id from likes l " +
                    "where l.item_id=i.id and l.user_id=:userId) as isLiked " +
                    "from items i " +
                    "join users u on i.created_by_id = u.id " +
                    "join collections c on c.id = i.collection_id ",
            countQuery = "select count(*) from items")
    Page<ItemProjection> getAllItems(Pageable pageable, UUID userId);
}
