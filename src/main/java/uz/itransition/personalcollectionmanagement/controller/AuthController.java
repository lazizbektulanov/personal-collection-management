package uz.itransition.personalcollectionmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.payload.RegisterDto;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;
import uz.itransition.personalcollectionmanagement.service.AuthService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;


    private final UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        Map<String, String> oauth2RegistrationUrls = authService.getOauth2RegistrationUrls();
        model.addAttribute("urls", oauth2RegistrationUrls);
        return "login";
    }

    @GetMapping("/oauth2/success")
    public String loginUser(OAuth2AuthenticationToken authentication) {
        return authService.loginUser(authentication);
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        Map<String, String> oauth2RegistrationUrls =
                authService.getOauth2RegistrationUrls();
        model.addAttribute("registerDto", new RegisterDto());
        model.addAttribute("urls", oauth2RegistrationUrls);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterDto registerDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        return authService.registerUser(registerDto, bindingResult);
    }
}
