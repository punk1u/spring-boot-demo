package tech.punklu.myspringboot.demo.mail;

import tech.punklu.myspringboot.demo.model.User;

import java.util.List;

/**
 * 发送邮件服务
 */
public interface SendJunkMailService {

    boolean sendJunkMail(List<User> users);
}
