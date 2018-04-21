package top.wangjingxin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = "/api/v1/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String url = servletRequest.getRequestURI();
        String user = servletRequest.getSession().getAttribute("id")+"";
        if(user.length()==32||(url.contains("/user")&&!url.contains("/user/check"))){
            chain.doFilter(request,response);
            return;
        }
        servletResponse.getWriter().write("error.not login");
    }

    @Override
    public void destroy() {}
}
