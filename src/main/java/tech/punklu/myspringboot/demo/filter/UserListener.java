package tech.punklu.myspringboot.demo.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 监听器
 *
 * @author punklu
 * @WebListener 注解用于将一个类声明为监听器，该注解将会在应用部署时被容器处理，容器根据具体的属性配置将相应的类部署为监听器
 * <p>
 * ServletContextListener：监听ServletContext对象的生命周期，当Servlet容器启动或终止web应用时，会触发ServletContextEvent事件，
 * 该事件由ServletContextListener类来处理。在ServletContextListener接口中定义了处理ServletContextEvent事件的两个方法：
 * contextInitialized和contextDestroyed
 * <p>
 * contextInitialized：当Servlet容器启动web应用时调用该方法。在调用完该方法后，容器再对Filter初始化，并且对那些在web应用启动时就
 * 需要被初始化的Servlet进行初始化
 * contextDestroyed：当Servlet容器终止web应用时调用该方法。在调用该方法之前，容器会先销毁所有的Servlet和Filter过滤器
 * <p>
 * 可以在contextInitialized方法中查询所有的用户，利用缓存技术把用户数据存放到缓存中，提高系统性能
 */
@WebListener
public class UserListener implements ServletContextListener {

    Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 查询数据库所有的用户
        List<User> users = userService.findAll();
        // 清除缓存中的数据
        redisTemplate.delete(ALL_USER);
        // 将数据存放到Redis缓存中
        redisTemplate.opsForList().leftPushAll(ALL_USER, users);
        // 在真实项目中需要注释掉，查询所有的用户数据
        // 0代表带一个元素，-1表示最后一个元素
        List<User> queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        System.out.println("缓存中目前的用户数有: " + queryUserList.size() + " 人");
        System.out.println("ServletContext上下文初始化");
        //logger.info("ServletContext上下文初始化");
        //logger.info("缓存中目前的用户数有: " + queryUserList.size() + " 人");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext上下文销毁");
        //logger.info("ServletContext上下文销毁");
    }
}
