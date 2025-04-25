package nashtech.rookie.uniform.shared.configurations.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nashtech.rookie.uniform.user.api.UserInfoProvider;
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
    private final String phoneNumber;
    private final UserInfoProvider userInfoProvider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userInfoProvider.getRole(phoneNumber)));
    }

    @Override
    public String getPassword() {
        return userInfoProvider.getPassword(phoneNumber);
    }

    @Override
    public String getUsername() {
        return getValidPhoneNumber();
    }

    public String getEmail() {
        return userInfoProvider.getEmail(phoneNumber);
    }

    public UUID getId() {
        return userInfoProvider.getId(phoneNumber);
    }

    public String getPhoneNumber() {
        return getValidPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userInfoProvider.isAccountNonLocked(phoneNumber);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userInfoProvider.isAccountEnabled(phoneNumber);
    }

    private String getValidPhoneNumber() {
        return userInfoProvider.isPhoneNumberExists(phoneNumber) ? phoneNumber : null;
    }
}
