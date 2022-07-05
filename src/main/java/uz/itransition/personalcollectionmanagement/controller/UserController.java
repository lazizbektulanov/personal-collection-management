package uz.itransition.personalcollectionmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.projection.user.ProfileProjection;
import uz.itransition.personalcollectionmanagement.projection.user.UserAccountProjection;
import uz.itransition.personalcollectionmanagement.projection.user.UserProjection;
import uz.itransition.personalcollectionmanagement.service.AuthService;
import uz.itransition.personalcollectionmanagement.service.UserService;

import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    //================================

//    private final OAuth2AuthorizedClientService authorizedClientService;

//    @GetMapping("/loginSuccess")
//    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient client = authorizedClientService
//                .loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//        System.out.println("CLIENT principal name:  " + client.getPrincipalName());
//        System.out.println("CLIENT registration: " + client.getClientRegistration().toString());
//        System.out.println("client.getClientRegistration()\n" +
//                "  .getProviderDetails().getUserInfoEndpoint().getUri() " + client.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUri());
//        System.out.println("CLIENT: " + client);
//        return "redirect:/home";
//    }

    //================================

    @GetMapping("/my-collections")
    public String getProfilePage(Model model) {
        ProfileProjection userProfile = userService.getUserProfile();
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("isOwner", true);
        return "profile-page";
    }

    @GetMapping("/edit-profile")
    public String getEditProfilePage(Model model) {
        UserAccountProjection userAccount =
                userService.getUserAccountInfo();
        model.addAttribute("userAcc", userAccount);
        return "edit-profile-page";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/management")
    public String getAllUsers(Model model,
                              @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                              @RequestParam(value = "sortBy", defaultValue = "email") String sortBy,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Page<UserProjection> users = userService.getAllUsers(page, sortBy, sortDir);
        model.addAttribute("users", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("sortDirection", sortDir);
        model.addAttribute("sortBy", sortBy);
        return "user-management";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/change-role/{userId}")
    public String changeUserRole(@PathVariable UUID userId) {
        userService.changeUserRole(userId);
        return "redirect:/user/management";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/change-status/{userId}")
    public String changeUserStatus(@PathVariable UUID userId) {
        userService.changeUserStatus(userId);
        return "redirect:/user/management";
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return "redirect:/user/management";
    }

    @GetMapping("/collections")
    public String userCollections(@RequestParam("userId") UUID userId,
                                  Model model) {
        ProfileProjection userProfile = userService.getUserProfile(userId);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("isOwner", authService.isCurrentUserAdmin());
        return "profile-page";
    }
}
