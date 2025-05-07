package nashtech.rookie.uniform.application.configuration.security;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static nashtech.rookie.uniform.application.constant.SecurityConstants.PERMIT_ONLY_GET_ENDPOINTS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Value("${front-end.domain}")
    private String frontEndDomain;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(SecurityConstants.PERMIT_ALL_ENDPOINTS).permitAll()
                        .requestMatchers(SecurityConstants.DOCUMENTATION_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PERMIT_ONLY_GET_ENDPOINTS).permitAll()

                        .requestMatchers(SecurityConstants.ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, SecurityConstants.ADMIN_WRITE_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, SecurityConstants.ADMIN_WRITE_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, SecurityConstants.ADMIN_WRITE_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, SecurityConstants.ADMIN_WRITE_ENDPOINTS).hasRole("ADMIN")

                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontEndDomain));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
