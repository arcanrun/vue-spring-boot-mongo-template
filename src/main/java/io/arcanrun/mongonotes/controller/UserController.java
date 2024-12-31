package io.arcanrun.mongonotes.controller;

import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.arcanrun.mongonotes.util.RestConstant.API_PATH;

@Tag(name = "Authentication controller")
@RestController
@RequestMapping(API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }
}