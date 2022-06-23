package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.repository.ItemRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;

    public List<ItemProjection> getLatestItems() {
        return itemRepository.findLatestItems();
    }

    public ItemByIdProjection getItemById(UUID itemId) {
        return itemRepository.getItemById(itemId);
    }

    public Page<CollectionItemsProjection> getCollectionItems(UUID collectionId, Integer page, String sortBy, String sortDir) {
        int pageSize = Integer.parseInt(Constants.ITEMS_DEFAULT_PAGE_SIZE);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return itemRepository.getItemsByCollectionId(pageable, collectionId);
    }
}
