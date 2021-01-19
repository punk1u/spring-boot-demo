package tech.punklu.myspringboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ServletComponentScan：使用该注解后，Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener 注解自动注册、无须其他代码。
 */
@SpringBootApplication
@ServletComponentScan
// 使Spring Boot扫描到某个配置文件
@ImportResource(locations = {"classpath:spring-mvc.xml"})
// 使用@Async注解之前，需要在入口类添加注解@EnableAsync开启异步调用。
@EnableAsync
// 开启Retry重试
@EnableRetry
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
