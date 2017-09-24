package tina.coffee.system.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tina.coffee.system.security.data.model.CoffeeRoleEntity;
import tina.coffee.system.security.data.model.CoffeeUserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoffeeUserDetails implements UserDetails {

    private final CoffeeUserEntity user;

    public CoffeeUserDetails(CoffeeUserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final CoffeeRoleEntity roleEntity : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public CoffeeUserEntity getUser() {
        return user;
    }
}
