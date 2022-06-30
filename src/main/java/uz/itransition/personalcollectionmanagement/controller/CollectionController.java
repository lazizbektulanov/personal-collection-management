package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.itransition.personalcollectionmanagement.payload.CollectionDto;
import uz.itransition.personalcollectionmanagement.projection.CustomFieldProjection;
import uz.itransition.personalcollectionmanagement.projection.CustomFieldValueProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionItemsProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;
import uz.itransition.personalcollectionmanagement.service.CollectionService;
import uz.itransition.personalcollectionmanagement.service.CustomFieldService;
import uz.itransition.personalcollectionmanagement.service.CustomFieldValueService;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;


@Controller
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {


    private final CollectionService collectionService;

    private final ItemService itemService;

    private final CustomFieldService customFieldService;

    private final CustomFieldValueService customFieldValueService;

    @GetMapping("/id/{collectionId}")
    public String getCollectionById(Model model,
                                    @PathVariable(value = "collectionId") UUID collectionId,
                                    @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                                    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        CollectionByIdProjection collection = collectionService.getCollectionById(collectionId);
        Page<CollectionItemsProjection> collectionItems =
                itemService.getCollectionItems(collectionId, page, sortBy, sortDir);
        List<CustomFieldProjection> customFields =
                customFieldService.getCollectionCustomFields(collectionId);
        model.addAttribute("collection", collection);
        model.addAttribute("collectionItems", collectionItems);
        model.addAttribute("customFields", customFields);
        model.addAttribute("isCollectionOwner", collectionService.isCollectionOwner(collectionId));

        model.addAttribute("sortDirection", sortDir);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", collectionItems.getTotalPages());
        return "collection-by-id";
    }

    @GetMapping("/create")
    public String getCreateCollectionPage(Model model) {
        model.addAttribute("dataTypesCustomField",
                collectionService.getCollectionTopics());
        return "create-collection";
    }

    @PostMapping("/create")
    public String createCollection(@RequestPart("collectionDto") CollectionDto collectionDto,
                                   @RequestPart(value = "image", required = false) MultipartFile collectionImage) throws IOException {
        collectionService.createCollection(collectionDto, collectionImage);
        return "redirect:/user/my-collections";
    }

    @GetMapping("/edit")
    public String getEditCollectionPage(@RequestParam("collectionId") UUID collectionId,
                                        Model model) {
        CollectionByIdProjection collectionById =
                collectionService.getCollectionById(collectionId);
        List<CustomFieldProjection> collectionCustomFields =
                customFieldService.getCollectionCustomFields(collectionId);
        model.addAttribute("collection",collectionById);
        model.addAttribute("collectionCustomFields",collectionCustomFields);
        return "edit-collection";

    }
}
