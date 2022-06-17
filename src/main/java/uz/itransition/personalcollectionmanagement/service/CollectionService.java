package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.projection.CollectionProjection;
import uz.itransition.personalcollectionmanagement.repository.CollectionRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public List<CollectionProjection> getLargestCollections() {
        return collectionRepository.getLargestCollections();
    }
}
