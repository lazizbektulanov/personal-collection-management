package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.customfield.CustomFieldValueProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemDto;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;
import uz.itransition.personalcollectionmanagement.service.CustomFieldService;
import uz.itransition.personalcollectionmanagement.service.ItemService;
import uz.itransition.personalcollectionmanagement.service.ItemSearchService;
import uz.itransition.personalcollectionmanagement.service.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    private final TagService tagService;

    private final CustomFieldService customFieldService;

    private final ItemSearchService itemSearchService;

    @GetMapping
    public String getItemById(@RequestParam("itemId") UUID itemId,
                              Model model) {
        ItemByIdProjection itemById = itemService.getItemById(itemId);
        List<CustomFieldValueProjection> itemCustomFieldValues =
                customFieldService.getItemCustomFieldValues(itemId);

        model.addAttribute("item", itemById);
        model.addAttribute("itemCustomFields", itemCustomFieldValues);
        return "item-page";
    }

    @GetMapping("/like")
    public String likeItem(@RequestParam("itemId") UUID itemId) {
//        System.out.println(request.getHeader("Referer"));
        itemService.likeItem(itemId);
        return "redirect:/item?itemId=" + itemId;
    }

    @GetMapping("/create")
    public String getItemCreatePage(@RequestParam("collectionId") UUID collectionId,
                                    Model model) {
        List<CustomField> itemCustomFields = itemService.getItemCustomFields(collectionId);
        List<Tag> itemTags = itemService.getAllTags();

        model.addAttribute("itemCustomFields", itemCustomFields);
        model.addAttribute("itemTags", itemTags);
        model.addAttribute("collectionId", collectionId);
        return "create-item-page";
    }

    @PostMapping("/create")
    public String createItem(@RequestParam("collectionId") UUID collectionId,
                             HttpServletRequest request) {
        itemService.createItem(request, collectionId);
        return "redirect:/collection/id/" + collectionId;
    }

    @GetMapping("/edit")
    public String getEditItemPage(@RequestParam("itemId") UUID itemId,
                                  Model model) {
        ItemByIdProjection item = itemService.getEditingItem(itemId);
        List<String> selectedItemTags =
                tagService.getSelectedItemTags(item.getTagsByItemId());
        List<CustomFieldValueProjection> itemCustomFieldValues =
                customFieldService.getItemCustomFieldValues(itemId);
        List<Tag> allTags = itemService.getAllTags();

        model.addAttribute("item", item);
        model.addAttribute("itemFields", itemCustomFieldValues);
        model.addAttribute("allTags", allTags);
        model.addAttribute("selectedItemTags", selectedItemTags);
        return "edit-item-page";
    }

    @PostMapping("/edit")
    public String editItem(@RequestParam("collectionId") UUID collectionId,
                           HttpServletRequest req) {
        itemService.editItem(req);
        return "redirect:/collection/id/" + collectionId;
    }

    @GetMapping("/tag")
    public String getItemsByTag(@RequestParam("tagId") UUID tagId,
                                @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                                Model model) {
        Page<ItemProjection> itemByTag = itemService.getItemsByTag(tagId, page);
        model.addAttribute("items", itemByTag);
        model.addAttribute("tagId", tagId);

        model.addAttribute("pageTitle", "Related");

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemByTag.getTotalPages());
        return "view-all-item-page";
    }

    @GetMapping("/all")
    public String getAllItems(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
            Model model) {
        Page<ItemProjection> allItems = itemService.getAllItems(page);
        model.addAttribute("items", allItems);
        model.addAttribute("allItemPage", true);

        model.addAttribute("pageTitle", "All");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", allItems.getTotalPages());
        return "view-all-item-page";
    }

    @GetMapping("/search")
    public String searchItem(@RequestParam("q") String keyword,
                             Model model) {
        List<ItemDto> itemList = itemSearchService.getItemList(keyword);
        model.addAttribute("item", itemList);
        model.addAttribute("items",itemList);

        model.addAttribute("pageTitle","Related");
        model.addAttribute("search",true);
        return "view-all-item-page";
    }
}
