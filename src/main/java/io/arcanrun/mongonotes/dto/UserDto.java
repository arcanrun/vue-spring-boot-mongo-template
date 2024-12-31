package io.arcanrun.mongonotes.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record UserDto(String id, String name, List<GrantedAuthority> authorities) {
}
