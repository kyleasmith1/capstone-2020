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
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.security.GeneralSecurityException;
import com.google.sps.data.RequestParser;
 
@WebFilter("/auth-filter")
public class AuthenticateFilter implements Filter {
 
    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException{

    }
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
 
        System.out.println("I WAS HERE");
        System.out.println(req.getHeader("authorization"));

        GoogleIdToken idToken = null;
        try {
            idToken = RequestParser.verifyTokenFromRequestHeader(req, "authorization");
        } catch (GeneralSecurityException e){
            System.out.println("Cannot verify token: " + e);
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e){
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
 
        System.out.println("Verified");
        
        chain.doFilter(request, response);
    }
    
    public void destroy() {
        
    }

}
