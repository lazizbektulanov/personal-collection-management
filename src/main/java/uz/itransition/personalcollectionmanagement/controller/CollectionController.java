package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.itransition.personalcollectionmanagement.entity.enums.CustomFieldType;
import uz.itransition.personalcollectionmanagement.payload.CollectionDto;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.service.CollectionService;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;


@Controller
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {


    private final CollectionService collectionService;

    private final ItemService itemService;

    private final CollectionRepository collectionRepository;


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

    @GetMapping("/delete/{collectionId}")
    public String deleteCollection(@PathVariable UUID collectionId){
        collectionRepository.deleteById(collectionId);
        return "register";
    }

    @GetMapping("/create")
    public String createCollection(Model model) {
//        model.addAttribute("dataTypesCustomField",collectionService.getCollectionTopics());
        return "create-collection";
    }

    @PostMapping("/create")
    public String createCollection(@RequestPart("collectionDto") CollectionDto collectionDto,
                                   @RequestPart("image") MultipartFile collectionImage) throws ServletException, IOException {
        collectionService.createCollection(collectionDto,collectionImage);
        return "redirect:/user/my-collections";
    }
}
