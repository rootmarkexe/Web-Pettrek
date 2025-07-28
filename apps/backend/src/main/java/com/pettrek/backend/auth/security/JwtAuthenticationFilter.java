package com.pettrek.backend.auth.security;

import com.pettrek.backend.auth.dto.ErrorResponse;
import com.pettrek.backend.auth.exception.JwtTokenExpiredException;
import com.pettrek.backend.auth.exception.JwtTokenInvalidException;
import com.pettrek.backend.auth.exception.JwtTokenUnsupportedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserDetailsService authUserDetailsSerivce;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthUserDetailsService authUserDetailsSerivce) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authUserDetailsSerivce = authUserDetailsSerivce;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try{
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt)){
                try{
                jwtTokenProvider.validateToken(jwt);
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = authUserDetailsSerivce.loadById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);}catch(JwtTokenInvalidException |
                                                                                            JwtTokenExpiredException |
                                                                                            JwtTokenUnsupportedException ex){
                    return;
                }
            }
        }catch(Exception e){
            logger.error("Не удалось установить объект аутентификации в Security Context", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
