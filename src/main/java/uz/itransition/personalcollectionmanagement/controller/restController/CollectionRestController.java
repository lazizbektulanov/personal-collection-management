package uz.itransition.personalcollectionmanagement.controller.restController;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.payload.CollectionDto;
import uz.itransition.personalcollectionmanagement.service.CollectionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection")
public class CollectionRestController {

    private final CollectionService collectionService;

    @PostMapping("/create")
    public void createCollection(@RequestBody CollectionDto collectionDto) {
        collectionService.createCollection(collectionDto);
    }
}
