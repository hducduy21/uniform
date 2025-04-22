package nashtech.rookie.uniform.configs.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.configs.security.CustomUserDetails;
import nashtech.rookie.uniform.configs.security.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //get token from header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !StringUtils.startsWith(authHeader,"Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = StringUtils.substring(authHeader, 7);

        //extract phoneNumber to identify user and push it to context holder
        String phoneNumber = jwtUtil.extractClaim(token, "phoneNumber");
        if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(phoneNumber);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
