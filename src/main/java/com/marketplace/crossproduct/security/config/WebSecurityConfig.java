package com.marketplace.crossproduct.security.config;

import com.marketplace.crossproduct.core.model.Portal;
import com.marketplace.crossproduct.security.AuthEntryPointJwt;
import com.marketplace.crossproduct.security.AuthTokenFilter;
import com.marketplace.crossproduct.security.CustomUserDetailsService;
import com.marketplace.crossproduct.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/attribute/definition").hasAnyAuthority(Role.PLATFORM_ADMIN.name(),
                                                                                                        Role.PORTAL_ADMIN.name())
                                .requestMatchers("/api/attribute/specificationValue").hasAnyAuthority(Role.PLATFORM_ADMIN.name(),
                                                                                                   Role.PORTAL_ADMIN.name())

                                .requestMatchers("/api/auth").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()

                                .requestMatchers("/api/portal").hasAnyAuthority(Role.PLATFORM_ADMIN.name())
                                .requestMatchers("/api/portal/**").hasAnyAuthority(Role.PLATFORM_ADMIN.name(),
                                                                                             Role.PORTAL_ADMIN.name(),
                                                                                             Role.PORTAL_USER.name())

                                .requestMatchers("/api/product").hasAuthority(Role.PLATFORM_ADMIN.name())
                                .requestMatchers("/api/product/standard").permitAll()
                                .requestMatchers("/api/product/details/**").hasAnyAuthority(Role.PLATFORM_ADMIN.name(),
                                                                                                      Role.PORTAL_ADMIN.name(),
                                                                                                      Role.PORTAL_USER.name())

                                .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}