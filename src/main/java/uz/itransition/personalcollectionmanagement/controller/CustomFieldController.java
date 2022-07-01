package uz.itransition.personalcollectionmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.itransition.personalcollectionmanagement.service.CollectionService;
import uz.itransition.personalcollectionmanagement.service.CustomFieldService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/custom-field")
public class CustomFieldController {

    private final CustomFieldService customFieldService;

    @GetMapping("/delete")
    public String deleteCustomField(@RequestParam("collectionId") UUID collectionId,
                                    @RequestParam("fieldId") UUID fieldId,
                                    RedirectAttributes attributes) {
        customFieldService.deleteCustomField(fieldId);
        return "redirect:/collection/edit?collectionId=" + collectionId;
    }

}
