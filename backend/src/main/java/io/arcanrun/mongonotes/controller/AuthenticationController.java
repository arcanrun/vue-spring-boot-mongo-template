package io.arcanrun.mongonotes.controller;

import io.arcanrun.mongonotes.dto.LoginRequestDto;
import io.arcanrun.mongonotes.dto.RegisterRequestDto;
import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.arcanrun.mongonotes.util.RestConstant.API_PATH;

@Tag(name = "Authentication controller")
@RestController
@RequestMapping(API_PATH + "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequestDto dto) {
    return authenticationService.authenticate(dto);
  }

  @PostMapping("/signup")
  public UserDto signup(@RequestBody RegisterRequestDto dto) {
    return authenticationService.register(dto);
  }

  @PutMapping("/refresh")
  public String refresh() {
    return authenticationService.refreshToken();
  }
}
