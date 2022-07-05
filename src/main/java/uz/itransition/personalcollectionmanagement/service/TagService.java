package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Tag;
import uz.itransition.personalcollectionmanagement.projection.TagProjection;
import uz.itransition.personalcollectionmanagement.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<String> getSelectedItemTags(List<TagProjection> tagsByItemId) {
        List<String> selectedTags = new ArrayList<>();
        for (TagProjection tagProjection : tagsByItemId) {
            selectedTags.add(tagProjection.getName());
        }
        return selectedTags;
    }
}
