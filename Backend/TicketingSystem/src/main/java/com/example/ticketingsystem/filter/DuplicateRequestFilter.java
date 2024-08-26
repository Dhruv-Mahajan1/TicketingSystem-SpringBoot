package com.example.ticketingsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class DuplicateRequestFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long CACHE_EXPIRATION_TIME = 5; // in minutes
    private static final String REQUEST_ID_HEADER = "RequestID";




    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestId = httpRequest.getHeader(REQUEST_ID_HEADER);

        if (requestId == null || requestId.isEmpty()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Missing or empty request ID header: " + REQUEST_ID_HEADER);
            return;
        }

        if (isDuplicateRequest(requestId)) {
            httpResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            httpResponse.getWriter().write("Duplicate request detected.");
            return;
        }

        try {

            chain.doFilter(request, response);
        } finally {

            redisTemplate.delete(requestId);
        }
    }

    private boolean isDuplicateRequest(String requestId) {
        String cachedValue = redisTemplate.opsForValue().get(requestId);

        if (cachedValue != null) {
            return true;
        }
        redisTemplate.opsForValue().set(requestId, "processed", CACHE_EXPIRATION_TIME, TimeUnit.MINUTES);
        return false;
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }


}
