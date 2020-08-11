package com.google.sps.filter;
 
import java.io.IOException;
import java.util.Enumeration;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
 
@WebFilter("/auth-filter")
public class AuthenticateFilter implements Filter {
 
    private ServletContext context;
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        // System.out.println(req.getHeaderNames());
 
        // System.out.println(req.getHeader("id_token"));
 
        System.out.println("I WAS HERE");
 
        chain.doFilter(request, response);
    }
 
}
