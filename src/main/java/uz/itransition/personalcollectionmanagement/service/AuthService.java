package uz.itransition.personalcollectionmanagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (EmailValidator.getInstance().isValid(username)) {
            return userRepository.findByEmail(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        } else {
            return userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        }
    }
}
