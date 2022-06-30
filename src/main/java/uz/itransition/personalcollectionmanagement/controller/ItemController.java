package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String getItemById(@RequestParam("id") UUID itemId,
                              Model model) {
        ItemByIdProjection itemById = itemService.getItemById(itemId);
        model.addAttribute("item", itemById);
        return "item-by-id";
    }

    @GetMapping("/like")
    public String likeItem() {
        return "register";
    }

    @GetMapping("/create")
    public String getItemCreatePage(@RequestParam("collectionId") UUID collectionId,
                                    Model model) {
        List<CustomField> itemCustomFields = itemService.getItemCustomFields(collectionId);
        List<Tag> itemTags = itemService.getItemTags();
        model.addAttribute("itemCustomFields", itemCustomFields);
        model.addAttribute("itemTags", itemTags);
        model.addAttribute("collectionId", collectionId);
        return "create-item";
    }

    @PostMapping("/create")
    public String createItem(@RequestParam("collectionId") UUID collectionId,
                             HttpServletRequest request) throws ServletException, IOException {
        itemService.createItem(request, collectionId);
        return "redirect:/collection/id/" + collectionId;
    }

    @GetMapping("/tag")
    public String getItemsByTag(@RequestParam("tagId") UUID tagId,
                                @RequestParam(value = "page",defaultValue = DEFAULT_PAGE) Integer page,
                                Model model) {
        Page<ItemProjection> itemByTag = itemService.getItemsByTag(tagId,page);
        model.addAttribute("items",itemByTag);
        model.addAttribute("tagId",tagId);

        //this model will define all item page title
        //relatedItems=true ==> for search and get items by tag
        //relatedItems=false ==> view all items
        model.addAttribute("itemByTagPage",true);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemByTag.getTotalPages());
        return "view-all-item-page";
    }

    @GetMapping("/all")
    public String getAllItems(
            @RequestParam(value = "page",defaultValue = DEFAULT_PAGE) Integer page,
            Model model){
        Page<ItemProjection> allItems = itemService.getAllItems(page);
        model.addAttribute("items",allItems);
        model.addAttribute("allItemPage",true);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", allItems.getTotalPages());
        return "view-all-item-page";
    }
}
