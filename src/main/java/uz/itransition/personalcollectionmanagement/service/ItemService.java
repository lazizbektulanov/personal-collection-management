package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.repository.ItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;

    public List<ItemProjection> getLatestItems(){
        return itemRepository.findLatestItems();
    }

    public ItemByIdProjection getItemById(UUID itemId) {
        return itemRepository.getItemById(itemId);
    }
}
