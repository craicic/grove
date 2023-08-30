package org.motoc.gamelibrary.configuration;

import org.motoc.gamelibrary.security.filters.JwtAuthenticationFilter;
import org.motoc.gamelibrary.security.filters.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .addFilter(jwtAuthenticationFilter)
//                .addFilterBefore(new BeforeEncodeUrlFilter(), DisableEncodeUrlFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public static CustomDsl customDsl() {
        return new CustomDsl();
    }

}
