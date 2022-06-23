package uz.itransition.personalcollectionmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.itransition.personalcollectionmanagement.projection.ProfileProjection;
import uz.itransition.personalcollectionmanagement.service.UserService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserController {


    private final UserService userService;



//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/my-collections")
    public String getProfilePage(Model model){
        ProfileProjection userProfile = userService.getUserProfile();
        model.addAttribute("userProfile",userProfile);
        return "profile";
    }
}
