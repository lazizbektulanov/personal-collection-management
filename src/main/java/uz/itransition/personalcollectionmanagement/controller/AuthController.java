package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.itransition.personalcollectionmanagement.payload.RegisterDto;
import uz.itransition.personalcollectionmanagement.service.AuthService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterDto registerDto, BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }
        return authService.registerUser(registerDto,bindingResult);
    }
}
