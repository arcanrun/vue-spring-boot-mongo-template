package io.arcanrun.mongonotes.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder
@Getter
public class User implements UserDetails {

    @Id
    private String id;

    private String password;

    private String username;

    @Builder.Default
    private List<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
