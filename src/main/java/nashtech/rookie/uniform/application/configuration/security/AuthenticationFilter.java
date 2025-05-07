package nashtech.rookie.uniform.application.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.constants.LogField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

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
        try {
            MDC.put(LogField.REQUEST_ID, UUID.randomUUID().toString());

            //get token from header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !StringUtils.startsWith(authHeader,"Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = StringUtils.substring(authHeader, 7);

            //extract email to identify user and push it to context holder
            String email = jwtUtil.extractClaim(token, "email");
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String userId = jwtUtil.extractClaim(token, "userId");
                MDC.put(LogField.USER_ID, userId);

                CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            filterChain.doFilter(request, response);
            MDC.clear();
        } finally {
            MDC.clear();
        }
    }
}
