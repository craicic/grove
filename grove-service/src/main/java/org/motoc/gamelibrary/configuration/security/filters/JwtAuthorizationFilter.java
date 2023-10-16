package org.motoc.gamelibrary.configuration.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

import static org.motoc.gamelibrary.technical.ApiConstants.*;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        logger.info("Request analyzed :" + request.getServletPath());
        return request.getServletPath().equals("/api/login")
               || request.getServletPath().startsWith("/api/public")
               || request.getServletPath().equals("/styles.css")
               || request.getServletPath().contains("/swagger-ui")
               || request.getServletPath().contains("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/api/token")) {
            logger.info("Request ignored : " + request.getServletPath());
            filterChain.doFilter(request, response);
        } else {
            logger.info("Filtering request ... " + request.getServletPath());
            String authorizationToken = request.getHeader(AUTH_HEADER);
            if (authorizationToken != null && authorizationToken.startsWith(PREFIX)) {
                try {
                    String jwt = authorizationToken.substring(PREFIX_LENGTH);
                    Algorithm algorithm = Algorithm.HMAC256(HMAC_SECRET);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    Collection<GrantedAuthority> authorities = new ArrayDeque<>();
                    List.of(decodedJWT.getClaim(CLAIM_ROLES)
                                    .asArray(String.class))
                            .forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
