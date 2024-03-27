package com.atwaha.sis.config;

import com.atwaha.sis.security.JwtAuthenticationFilter;
import com.atwaha.sis.security.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import static com.atwaha.sis.model.enums.Role.ADMIN;

@Configuration
@EnableJpaAuditing()
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
                .authorizeHttpRequests(requests -> {

                            requests
                                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/v1/auth/login")
                                    .permitAll();
                            requests
                                    .requestMatchers("/api/v1/auth/register")
                                    .hasRole(ADMIN.name());

                            requests
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
                    logout.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
                });
        return http.build();
    }
}
