package io.arcanrun.mongonotes.service;

import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.entity.User;
import io.arcanrun.mongonotes.exception.ResourceNotFoundException;
import io.arcanrun.mongonotes.mapper.UserMapper;
import io.arcanrun.mongonotes.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDto getCurrentUserDto() {
    var securityContext = SecurityContextHolder.getContext();
    var authentication = securityContext.getAuthentication();
    var principal = (User) authentication.getPrincipal();

    return userRepository
        .findById(principal.getId())
        .map(userMapper::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  public User getCurrentUser() {
    var securityContext = SecurityContextHolder.getContext();
    var authentication = securityContext.getAuthentication();
    var principal = (User) authentication.getPrincipal();

    return userRepository
        .findById(principal.getId())
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
