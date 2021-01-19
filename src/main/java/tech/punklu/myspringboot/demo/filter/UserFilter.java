package tech.punklu.myspringboot.demo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 过滤器类
 *
 * @author punklu
 * @WebFilter：用于将一个类声明为过滤器，会被容器根据具体的属性配置将相应的类部署为过滤器。 这样就不需要在web.xml文件中配置相关描述信息。该注解常用属性有filterName、urlPatterns、value等
 * 除了这个直接配置类外，还需要在入口类中添加注解@ServletComponentScan
 */
@WebFilter(filterName = "UserFilter", urlPatterns = "/*")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("------->>> init");
    }

    @Override
    public void destroy() {
        System.out.println("------->>> destroy");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------->>> doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
