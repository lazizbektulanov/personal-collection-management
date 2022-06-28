package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.repository.CustomFieldRepository;
import uz.itransition.personalcollectionmanagement.repository.ItemRepository;
import uz.itransition.personalcollectionmanagement.repository.TagRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;

    private final CustomFieldRepository customFieldRepository;

    private final TagRepository tagRepository;

    public List<ItemProjection> getLatestItems() {
        return itemRepository.findLatestItems();
    }

    public ItemByIdProjection getItemById(UUID itemId) {
        return itemRepository.getItemById(itemId);
    }

    public Page<CollectionItemsProjection> getCollectionItems(UUID collectionId, Integer page, String sortBy, String sortDir) {
        int pageSize = Integer.parseInt(Constants.DEFAULT_PAGE_SIZE);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return itemRepository.getItemsByCollectionId(pageable, collectionId);
    }

    public List<CustomField> getItemCustomFields(UUID collectionId) {
        return customFieldRepository.findByCollectionId(collectionId);
    }

    public List<Tag> getItemTags() {
        return tagRepository.findAll();
    }

    public void createItem(HttpServletRequest request, UUID collectionId) {
        List<CustomField> customFields =
                customFieldRepository.findByCollectionId(collectionId);
        System.out.println("Name: "+request.getParameter("itemName"));
        System.out.println("Tags: "+request.getParameter("itemTags"));
        for (CustomField customField : customFields) {
            System.out.println(customField.getFieldName()+" "+
                    request.getParameter(customField.getFieldName()));
        }
    }
}
