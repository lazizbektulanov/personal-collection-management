package uz.itransition.personalcollectionmanagement.controller.restController;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.personalcollectionmanagement.projection.ItemProjection;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ItemController {


    private final ItemService itemService;

    @GetMapping("/open/latest-items")
    public List<ItemProjection> getLatestItems() {
        return itemService.getLatestItems();
    }
}
