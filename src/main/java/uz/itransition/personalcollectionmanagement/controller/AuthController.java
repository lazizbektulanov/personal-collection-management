package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uz.itransition.personalcollectionmanagement.service.AuthService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/home")
//    public String getHomePage() {
//        return "home";
//    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
