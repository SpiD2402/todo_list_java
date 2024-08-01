package com.example.todo_list.security;

import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class JwtFilter  extends OncePerRequestFilter {

    private JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtFilter(JwtService jwtService, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var requestTokenHeader= request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer "))
        {
            token = requestTokenHeader.substring(7);
            try{
                username = jwtService.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException    e)
            {
               throw  e;

            }
        }

        if (Objects.nonNull(username) &&  Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
        {
            final var userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (userDetails.isEnabled() && this.jwtService.validateToken(token,userDetails))
            {
                var usernameAndPasswordAuthToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernameAndPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthToken);
            }

        }
        filterChain.doFilter(request,response);


    }

}
