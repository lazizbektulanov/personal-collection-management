package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


}
