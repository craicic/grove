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

import java.io.EOFException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Map<String,String>> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Refresh token");
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            try {
                String jwtRefreshToken = authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("secret");
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
                String username = decodedJWT.getSubject();
                UserDetails user = detailsManager.loadUserByUsername(username);
                System.out.println(user.getUsername());
                String jwtAccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 5 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("access_token", jwtAccessToken);
                idToken.put("refresh_token", jwtRefreshToken);

                response.setContentType("application/json");
                return ResponseEntity.ok(idToken);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new IOException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Refresh token required!!");
        }
    }
}
