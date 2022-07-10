package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.projection.item.ItemDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSearchService {

    private final EntityManager entityManager;

    public List<Item> searchItems(String keyword) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Item.class)
                .get();

        Query query = queryBuilder.keyword().wildcard()
                .onFields(
                        "name",
                        "tags.name",
                        "collection.title",
                        "comments.body")
                .matching(keyword + "*")
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager
                .createFullTextQuery(query, Item.class);

        @SuppressWarnings("unchecked")
        List<Item> result = fullTextQuery.getResultList();
        return result;

    }

    public List<ItemDto> getItemList(String keyword) {
        List<Item> list = searchItems(keyword);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : list) {
            itemDtos.add(new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getCreatedBy().getProfileImgUrl(),
                    item.getCreatedBy().getFullName(),
                    item.getCollection().getTitle()
            ));
        }
        return itemDtos;
    }

//    public List<?> searchItems(String keyword) {
//        FullTextEntityManager fullTextEntityManager =
//                Search.getFullTextEntityManager(entityManager);
//
//        QueryBuilder queryBuilder = fullTextEntityManager
//                .getSearchFactory()
//                .buildQueryBuilder()
//                .forEntity(Item.class)
//                .get();
//
//        Query query = queryBuilder.keyword().wildcard()
//                .onFields("name", "tags.name", "collection.title")
//                .matching(keyword + "*")
//                .createQuery();
//
//        FullTextQuery fullTextQuery = fullTextEntityManager
//                .createFullTextQuery(query, Item.class);
//        fullTextQuery.setProjection(
//                FullTextQuery.ID,
//                "name",
//                "collection.title",
//                "createdBy.fullName"
//        );
//        return getItemList(fullTextQuery.getResultList());
//    }

//    private List<ItemDto> getItemList(List<Object[]> resultList) {
//        List<ItemDto> itemList = new ArrayList<>();
//        for (Object[] objects : resultList) {
//            ItemDto itemDto = new ItemDto();
//            itemDto.setId(objects[0].toString());
//            itemDto.setItemName((String) objects[1]);
//            itemDto.setItemCollectionTitle((String) objects[2]);
//            itemDto.setAuthorName((String) objects[3]);
//        }
//        return itemList;
//    }

}
