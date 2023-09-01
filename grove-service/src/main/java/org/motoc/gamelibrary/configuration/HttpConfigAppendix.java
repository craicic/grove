package org.motoc.gamelibrary.configuration;

import org.motoc.gamelibrary.configuration.security.filters.JwtAuthenticationFilter;
import org.motoc.gamelibrary.configuration.security.filters.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class HttpConfigAppendix extends AbstractHttpConfigurer<HttpConfigAppendix, HttpSecurity> {

    private static HttpConfigAppendix instance;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        if (authenticationManager == null)
            throw new Exception("AuthenticationManager.class can't be shared by HttpSecurity");

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // Singleton pattern (refactoring.guru)
    public static HttpConfigAppendix getInstance() {
        if (instance == null) {
            instance = new HttpConfigAppendix();
        }
        return instance;
    }
}
