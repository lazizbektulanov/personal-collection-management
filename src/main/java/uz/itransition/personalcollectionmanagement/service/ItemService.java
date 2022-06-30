package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.repository.CustomFieldRepository;
import uz.itransition.personalcollectionmanagement.repository.ItemRepository;
import uz.itransition.personalcollectionmanagement.repository.TagRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final CustomFieldRepository customFieldRepository;

    private final TagRepository tagRepository;

    private final AuthService authService;

    private final CollectionRepository collectionRepository;

    private final CustomFieldValueService customFieldValueService;

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

    public List<Tag> getItemTags(HttpServletRequest request) {
        String[] itemTags = request.getParameterValues("itemTags");
        List<UUID> itemTagsIds = new ArrayList<>();
        for (String itemTag : itemTags) {
            itemTagsIds.add(UUID.fromString(itemTag));
        }
        return tagRepository.findAllById(itemTagsIds);
    }

    public void createItem(HttpServletRequest request, UUID collectionId) {
        User currentUser = authService.getCurrentUser();
        List<Tag> itemTags = getItemTags(request);
        Item savedItem = itemRepository.save(new Item(
                        request.getParameter("itemName").trim(),
                        itemTags,
                        currentUser,
                        collectionRepository.getById(collectionId)
                )
        );
        customFieldValueService.saveItemCustomFieldValues(request, savedItem);
    }



}
