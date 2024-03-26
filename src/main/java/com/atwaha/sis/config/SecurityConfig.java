package com.atwaha.sis.config;

import com.atwaha.sis.security.JwtAuthenticationFilter;
import com.atwaha.sis.security.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.atwaha.sis.model.enums.Role.ADMIN;
import static com.atwaha.sis.model.enums.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**")
                                    .permitAll();

                            authorizeRequests
                                    .requestMatchers("/api/v1/management/**")
                                    .hasAnyRole(ADMIN.name(), MANAGER.name());

                            authorizeRequests
                                    .requestMatchers("/api/v1/admin/**")
                                    .hasRole(ADMIN.name());

                            authorizeRequests
                                    .anyRequest()
                                    .authenticated();
                        }
                ).sessionManagement(sessionManagement ->
                        /*Since every request is authenticated, we need the session creation policy to be stateless*/
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout.logoutUrl("/api/v1/auth/logout");
                    logout.addLogoutHandler(logoutHandler);
                    logout.clearAuthentication(true);
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        response.setStatus(HttpStatus.OK.value());
                    });
                    logout.clearAuthentication(true);
                });
        return http.build();
    }
}
