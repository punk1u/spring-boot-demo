package tech.punklu.myspringboot.demo.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启监控功能的方式有多种：
 * 1、使用原生的Servlet、Filter方式，然后通过@ServletComponentScan启动扫描包进行处理
 * 2、使用代码注册Servlet和Filter的方式处理，这也是Spring Boot推荐的方式，所以这里使用这种方式实现
 */

// Druid监控配置类,@Configuration注解相当于把这个类变成了一个Spring XML配置文件
@Configuration
public class DruidConfiguration {

    /**
     * Servlet的注册
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        // ServletRegistrationBean提供类的进行注册
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加初始化参数：initParams
        // 白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单(存在共同时，deny优先于allow)
        // 如果满足deny，就提示：Sorry，you are not permitted to view this page
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        // 登录查看信息的账号和密码
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    // @Bean注解等同于XML配置文件中的<bean>配置。Spring Boot会把加上该注解方法的返回值装载进Spring IOC容器，方法的名称对应<bean>标签的id属性值

    /**
     * Filter的注册
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg.*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
