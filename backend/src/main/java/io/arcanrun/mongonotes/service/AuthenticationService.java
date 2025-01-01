package io.arcanrun.mongonotes.service;

import io.arcanrun.mongonotes.config.security.Authority;
import io.arcanrun.mongonotes.config.security.jwt.JwtTokenProvider;
import io.arcanrun.mongonotes.dto.LoginRequestDto;
import io.arcanrun.mongonotes.dto.RegisterRequestDto;
import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.entity.User;
import io.arcanrun.mongonotes.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(LoginRequestDto dto) {
        var user = userService.getBy(dto.username())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid username or password");
        }

        return jwtTokenProvider.createTokenFor(user);
    }

    public UserDto register(RegisterRequestDto dto) {
        checkIfUserAlreadyExists(dto);

        return Optional.of(userService
                        .save(User.builder()
                                .username(dto.username())
                                .password(passwordEncoder.encode(dto.password()))
                                .authorities(Arrays
                                        .stream(Authority.values())
                                        .map(a -> (GrantedAuthority) new SimpleGrantedAuthority(a.name()))
                                        .toList())
                                .build()))
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getAuthorities()))
                .orElseThrow(() -> new UnauthorizedException("Something went wrong"));
    }

    public String refreshToken() {
        var currentUser = userService.getCurrentUser();

        return jwtTokenProvider.createTokenFor(currentUser);
    }

    private void checkIfUserAlreadyExists(RegisterRequestDto dto) {
        userService.getBy(dto.username())
                .ifPresent(u -> {
                    throw new UnauthorizedException("The name `%s` is already taken. Please try other names."
                            .formatted(u.getUsername()));
                });
    }
}
