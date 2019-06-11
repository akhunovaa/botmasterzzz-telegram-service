package com.botmasterzzz.telegram.filter;

import com.botmasterzzz.telegram.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final CustomException customException = new CustomException(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав", "Отсутствуют права на просмотр данного ресурса");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        customException.setPath(httpServletRequest.getServletPath());
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                chain.doFilter(request, response);
            } catch (NestedServletException e) {
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                final ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage());
            }
        } else {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage());
        }

    }
}