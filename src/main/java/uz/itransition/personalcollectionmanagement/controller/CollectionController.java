package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionByIdProjection;
import uz.itransition.personalcollectionmanagement.service.CollectionService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {


    private final CollectionService collectionService;


    @GetMapping("/create")
    public String createCollection() {
        return "create-collection";
    }

    @GetMapping("/{collectionId}")
    public String getCollectionById(@PathVariable UUID collectionId, Model model) {
        CollectionByIdProjection collection = collectionService.getCollectionById(collectionId);
        model.addAttribute("collection", collection);
        return "collection-by-id";
    }
}
