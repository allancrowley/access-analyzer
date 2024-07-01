package com.access.filter;

import com.access.service.IpFilterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class IpFilter extends GenericFilterBean {
    private final IpFilterService ipFilterService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String[] ipArray = request.getRemoteAddr().split("\\.");
        String ipSubnet = ipArray[0] + "." + ipArray[1] + "." + ipArray[2];
        if (ipFilterService.checkIp(ipSubnet)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Your IP is blocked due to suspected DDoS attack.");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
