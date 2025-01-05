package io.arcanrun.mongonotes.mapper;

import io.arcanrun.mongonotes.dto.UserDto;
import io.arcanrun.mongonotes.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;

@Mapper
public interface UserMapper {
  @Mapping(target = "authorities", qualifiedByName = "toAuthoritiesStringList")
  @Mapping(target = "name", source = "username")
  UserDto toDto(User source);

  @Named("toAuthoritiesStringList")
  default List<String> toAuthoritiesStringList(List<GrantedAuthority> source) {
    return source.stream().map(GrantedAuthority::getAuthority).toList();
  }
}
