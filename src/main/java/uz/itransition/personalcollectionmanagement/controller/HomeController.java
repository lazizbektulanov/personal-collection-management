package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.TagProjection;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.service.CollectionService;
import uz.itransition.personalcollectionmanagement.service.ItemService;
import uz.itransition.personalcollectionmanagement.service.TagService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final ItemService itemService;

    private final CollectionService collectionService;

    private final TagService tagService;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<ItemProjection> latestItems = itemService.getLatestItems();
        List<CollectionProjection> largestCollections = collectionService.getLargestCollections();
        List<Tag> tagCloud = tagService.getAllTags();
        model.addAttribute("latestItems", latestItems);
        model.addAttribute("largestCollections", largestCollections);
        model.addAttribute("tags",tagCloud);
        return "home";
    }
}
