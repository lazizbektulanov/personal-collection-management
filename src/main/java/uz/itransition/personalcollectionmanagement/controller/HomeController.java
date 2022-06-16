package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.itransition.personalcollectionmanagement.projection.ItemProjection;
import uz.itransition.personalcollectionmanagement.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final ItemService itemService;

    @GetMapping("/home")
    public String getHomePage(Model model){
        List<ItemProjection> latestItems = itemService.getLatestItems();
        model.addAttribute("latestItems",latestItems);
        return "home";
    }
}
