package tech.punklu.myspringboot.demo.quartz;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.punklu.myspringboot.demo.mail.SendJunkMailService;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用注解的定时器类
 *
 * @author punklu
 */

/**
 * @Configurable:加上此注解的类相当于XML配置文件，可以被Spring Boot扫描初始化
 * @EnableScheduling：通过在配置类注解@EnableScheduling开启对计划任务的支持，然后在要执行计划任务的方法上注解@Scheduled， 声明这是一个计划任务。
 * @Scheduled：注解为定时任务，在Cron表达式里写执行的时机。
 */
@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {

    @Resource
    private SendJunkMailService sendJunkMailService;

    @Resource
    private UserService userService;

    // 每5秒执行一次
    @Scheduled(cron = "*/5 * *  * * * ")
    public void reportCurrentByCron() {
        List<User> userList = userService.findAll();
        /*if (userList == null || userList.size() <= 0){
            return;
        }*/
        // 发送邮件
        sendJunkMailService.sendJunkMail(userList);
        System.out.println("定时器运行了!!!");
    }
}
