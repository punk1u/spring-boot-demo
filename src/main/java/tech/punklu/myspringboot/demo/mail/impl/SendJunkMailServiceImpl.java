package tech.punklu.myspringboot.demo.mail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.punklu.myspringboot.demo.mail.SendJunkMailService;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * 发送用户邮件服务
 */
@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {

    /**
     * 邮件发送接口
     */
    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private UserService userService;

    /**
     * @Value注解将application.properties配置文件中的配置设置到属性中
     */
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendJunkMail(List<User> users) {
        try {
            if (users == null || users.size() <= 0) {
                return Boolean.FALSE;
            }
            for (User user : users) {
                MimeMessage mimeMessage = this.mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                // 邮件发送方
                messageHelper.setFrom(from);
                // 邮件主题
                messageHelper.setSubject("地瓜今日特卖");
                // 邮件接收方
                messageHelper.setTo("1223756773@qq.com");
                // 邮件内容
                messageHelper.setText(user.getName() + ",你知道吗？厦门地瓜今日特卖，一斤只要9元");
                // 发送邮件
                this.mailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            System.out.println("sendJunkMail error" + e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
