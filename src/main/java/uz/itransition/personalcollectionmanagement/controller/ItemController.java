package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.itransition.personalcollectionmanagement.projection.item.ItemByIdProjection;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public String getItemById(@PathVariable UUID itemId,
                              Model model) {
        ItemByIdProjection itemById = itemService.getItemById(itemId);
        model.addAttribute("item",itemById);
        return "item-page";
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    @GetMapping("/like")
    public String likeItem(){
        return "register";
    }
}
