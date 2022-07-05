package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Role;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.entity.enums.RoleName;
import uz.itransition.personalcollectionmanagement.projection.ProfileProjection;
import uz.itransition.personalcollectionmanagement.projection.UserAccountProjection;
import uz.itransition.personalcollectionmanagement.projection.UserProjection;
import uz.itransition.personalcollectionmanagement.repository.RoleRepository;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;
import uz.itransition.personalcollectionmanagement.utils.Constants;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthService authService;

    private final SessionRegistry sessionRegistry;

    public ProfileProjection getUserProfile() {
        User currentUser = authService.getCurrentUser();
        return userRepository.getUserProfile(currentUser.getId());
    }

    public ProfileProjection getUserProfile(UUID userId) {
        return userRepository.getUserProfile(userId);
    }

    public Page<UserProjection> getAllUsers(Integer page, String sortBy, String sortDir) {
        int pageSize = Integer.parseInt(Constants.DEFAULT_PAGE_SIZE);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return userRepository.getAllUsers(pageable);
    }

    public void changeUserRole(UUID userId) {
        User user = userRepository.getById(userId);
        Role roleAdmin = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);
        Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER);
        user.setRole(user.getRole().getRoleName().equals(RoleName.ROLE_ADMIN) ?
                roleUser : roleAdmin);
        userRepository.save(user);
        clearUserSessions(user);
    }

    public void changeUserStatus(UUID userId) {
        //todo change to Optional in order to avoid NullPointer Exception
        User user = userRepository.getById(userId);
        if (user.isActive()) {
            user.setActive(false);
            clearUserSessions(user);
        } else {
            user.setActive(true);
        }
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        User user = userRepository.getById(userId);
        clearUserSessions(user);
        userRepository.deleteById(userId);
    }

    public void clearUserSessions(User user) {
        List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
        for (Object principal : loggedUsers) {
            if (principal instanceof User) {
                final User loggedUser = (User) principal;
                if (user.getUsername().equals(loggedUser.getUsername())) {
                    List<SessionInformation> sessionsInfo =
                            sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInformation : sessionsInfo) {
                        sessionRegistry.getSessionInformation(
                                sessionInformation.getSessionId()
                        ).expireNow();
                    }
                }
            }
            if (principal instanceof DefaultOidcUser) {
                final DefaultOidcUser loggedUser = (DefaultOidcUser) principal;
                if (user.getUsername().equals(loggedUser.getEmail())) {
                    List<SessionInformation> sessionsInfo =
                            sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInformation : sessionsInfo) {
                        sessionRegistry.getSessionInformation(
                                sessionInformation.getSessionId()
                        ).expireNow();
                    }
                }
            }
        }
    }

    public UserAccountProjection getUserAccountInfo() {
        User currentUser = authService.getCurrentUser();
        return userRepository.getUserAccountInfo(currentUser.getId());
    }
}
