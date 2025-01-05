package io.arcanrun.mongonotes.dto;

import java.util.List;

public record UserDto(String id, String name, List<String> authorities) {}
