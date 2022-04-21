package com.example.Web.Project.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        String path = request1.getRequestURI();
        if(request1.getSession(false) !=null)
        {
            if(path.equals("/task/groupTask"))
            {
                if(request1.getSession(false).getAttribute("curProjectID") != null)
                {
                    chain.doFilter(request,response);
                }
                else
                {
                    response1.sendError(404);
                }
            }
            else if(path.equals("/project/chat") || path.equals("/task/individualTask"))
            {
                if(request1.getSession(false).getAttribute("curProjectID") != null && request1.getSession(false).getAttribute("userID")!=null)
                {
                    chain.doFilter(request,response);
                }
                else
                {
                    response1.sendError(404);
                }
            }
            else
            {
                chain.doFilter(request,response);
            }
        }
        else {
            response1.sendError(401);
        }
    }



}
