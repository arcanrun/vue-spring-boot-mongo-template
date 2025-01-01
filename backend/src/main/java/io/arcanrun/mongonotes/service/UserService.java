package io.arcanrun.mongonotes.service;

import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.entity.User;
import io.arcanrun.mongonotes.exception.ResourceNotFoundException;
import io.arcanrun.mongonotes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserDto getCurrentUser() {
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();
        var principal = (User) authentication.getPrincipal();

        return userRepository.findById(principal.getId())
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getAuthorities()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Optional<User> getBy(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}

