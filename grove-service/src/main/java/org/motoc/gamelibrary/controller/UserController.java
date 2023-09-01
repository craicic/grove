package org.motoc.gamelibrary.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.motoc.gamelibrary.technical.ApiConstants.*;

@RestController
@CrossOrigin
@RequestMapping("api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final InMemoryUserDetailsManager detailsManager;

    @Autowired
    public UserController(InMemoryUserDetailsManager detailsManager) {
        this.detailsManager = detailsManager;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Refresh token");
        String authToken = request.getHeader(AUTH_HEADER);
        if (authToken != null && authToken.startsWith(PREFIX)) {
            try {
                String jwtRefreshToken = authToken.substring(LENGTH);
                Algorithm algorithm = Algorithm.HMAC256(HMAC_SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
                String username = decodedJWT.getSubject();
                UserDetails user = detailsManager.loadUserByUsername(username);
                logger.info("User : " + user.getUsername() + " was fetched... Checking roles...");
                String jwtAccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(CLAIM_ROLES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put(ACCESS_TOKEN, jwtAccessToken);
                idToken.put(REFRESH_TOKEN, jwtRefreshToken);

                response.setContentType("application/json");
                return ResponseEntity.ok(idToken);
            } catch (Exception e) {
                logger.warn(e.getMessage());
                throw new IOException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Refresh token is required!");
        }
    }
}
