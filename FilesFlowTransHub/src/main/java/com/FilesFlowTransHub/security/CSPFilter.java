package com.FilesFlowTransHub.security;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.FilesFlowTransHub.util.SystemUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CSPFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
 
        String nonce = SystemUtils.generateRandomAlphanumeric(20);
        request.setAttribute("nonce", nonce);
 
        String csp = "default-src 'self'; " +
                     "script-src 'self' https://cdn.jsdelivr.net 'nonce-" + nonce + "'; " +
                     "style-src 'self' https://cdn.jsdelivr.net 'unsafe-inline';"+ 
                     "img-src 'self' data:;" +
                     "object-src 'none'; " +  
                     "frame-ancestors 'none';";  
        response.setHeader("Content-Security-Policy", csp);

        filterChain.doFilter(request, response);
    }
}