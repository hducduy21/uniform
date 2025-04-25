package nashtech.rookie.uniform.application.configuration.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails
{
    private final UserInfoDto user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public UUID getId() {
        return user.getId();
    }

    public String getPhoneNumber() {return user.getPhoneNumber();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
