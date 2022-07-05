package uz.itransition.personalcollectionmanagement.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.entity.enums.RoleName;
import uz.itransition.personalcollectionmanagement.payload.RegisterDto;
import uz.itransition.personalcollectionmanagement.repository.RoleRepository;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static uz.itransition.personalcollectionmanagement.utils.Constants.AUTHORIZATION_REQUEST_BASE_URI;
import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_PROFILE_IMG_URL;

@Service
public class AuthService implements UserDetailsService,
        ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;


    public AuthService(UserRepository userRepository,
                       @Lazy PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if (event.getAuthentication().getPrincipal() instanceof User) {
            User user = (User) event.getAuthentication().getPrincipal();
            user.setLastLoginTime(Timestamp.from(Instant.now()));
            userRepository.save(user);
        }

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().equals("anonymousUser") ? new User() :
                (User) authentication.getPrincipal();
    }

    public boolean isCurrentUserAdmin() {
        User user = getCurrentUser();
        if (user.getId() == null) return false;
        else return user.getRole().getRoleName().equals(RoleName.ROLE_ADMIN);
    }

    public String registerUser(RegisterDto registerDto, BindingResult bindingResult) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "registerDto.confirmPassword",
                    "Please confirm your password");
            return "register";
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            bindingResult.rejectValue("email", "registerDto.email",
                    "An account for this email already exists");
            return "register";
        }
        userRepository.save(new User(
                registerDto.getFullName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                true,
                roleRepository.findByRoleName(RoleName.ROLE_USER),
                DEFAULT_PROFILE_IMG_URL
        ));
        return "redirect:/login";
    }

    public UsernamePasswordAuthenticationToken registerUser(String fullName, String email) {
        User savedUser = userRepository.save(new User(
                fullName,
                email,
                passwordEncoder.encode(UUID.randomUUID().toString()),
                true,
                DEFAULT_PROFILE_IMG_URL,
                Timestamp.from(Instant.now()),
                roleRepository.findByRoleName(RoleName.ROLE_USER)
        ));
        return new UsernamePasswordAuthenticationToken(
                savedUser,
                null,
                savedUser.getAuthorities());
    }

    public Map<String, String> getOauth2RegistrationUrls() {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        Map<String, String> oauth2AuthenticationUrls
                = new HashMap<>();
        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration ->
                    oauth2AuthenticationUrls.put(registration.getClientName(),
                            AUTHORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId()));
        }
        return oauth2AuthenticationUrls;
    }

    public String loginUser(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userInfoEndpointUri = client
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();
        if (!userInfoEndpointUri.isEmpty()) {
            Map userAttributes = getUserAttributes(client, userInfoEndpointUri); // user information
            String fullName = (String) userAttributes.get("name");
            String email = (String) userAttributes.get("email");
            Optional<User> userByEmail = userRepository.findByEmail(email);
            UsernamePasswordAuthenticationToken authenticationToken;
            authenticationToken = userByEmail.map(this::authenticateUser).orElseGet(() -> registerUser(fullName, email));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    private UsernamePasswordAuthenticationToken authenticateUser(User userByEmail) {
        userByEmail.setLastLoginTime(Timestamp.from(Instant.now()));
        userRepository.save(userByEmail);
        return new UsernamePasswordAuthenticationToken(
                userByEmail,
                null,
                userByEmail.getAuthorities());
    }

    public Map getUserAttributes(OAuth2AuthorizedClient client, String userInfoEndpointUri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                .getTokenValue());
        HttpEntity<?> entity = new HttpEntity<>("", headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoEndpointUri,
                HttpMethod.GET,
                entity,
                Map.class);
        return response.getBody();
    }
}
