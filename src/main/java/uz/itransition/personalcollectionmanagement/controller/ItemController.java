package uz.itransition.personalcollectionmanagement.controller;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.entity.CustomField;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
}
