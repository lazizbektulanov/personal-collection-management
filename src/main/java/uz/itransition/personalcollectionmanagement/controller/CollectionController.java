package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.service.CollectionService;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;


@Controller
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {


    private final CollectionService collectionService;

    private final ItemService itemService;


    @GetMapping("/create")
    public String createCollection() {
        return "create-collection";
    }

    @GetMapping("/{collectionId}")
    public String getCollectionById(Model model,
                                    @PathVariable(value = "collectionId") UUID collectionId,
                                    @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                                    @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
                                    @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir) {
        if (collectionService.checkCollectionOwner(collectionId)) {
            CollectionByIdProjection collection = collectionService.getCollectionById(collectionId);
            Page<CollectionItemsProjection> collectionItems = itemService.getCollectionItems(collectionId, page, sortBy, sortDir);
            model.addAttribute("collection", collection);
            model.addAttribute("collectionItems", collectionItems);
            model.addAttribute("sortDirection", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");
        }
        return "collection-by-id";
    }
}
