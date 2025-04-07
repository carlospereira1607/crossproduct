package com.marketplace.crossproduct.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.crossproduct.core.exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt  implements AuthenticationEntryPoint {

//    @Override
//    public void commence(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException authException
//    ) throws IOException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
//    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                new ApiError("ERR_UNAUTHORIZED", "Authentication required", HttpStatus.UNAUTHORIZED.value())
        ));
    }

}