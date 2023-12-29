package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter (urlPatterns = {"/login", "/register"})
public class LoginRegisterFilter extends HttpFilter {


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("user")==null){
            chain.doFilter(req,res);
        }
        else {
            res.sendRedirect("/home");
        }
    }
}
