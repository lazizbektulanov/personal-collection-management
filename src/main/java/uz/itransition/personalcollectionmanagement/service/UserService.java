package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.projection.ProfileProjection;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final AuthService authService;

    public ProfileProjection getUserProfile() {
        User currentUser = authService.getCurrentUser();
        return userRepository.getUserProfile(currentUser.getId());
    }
}
