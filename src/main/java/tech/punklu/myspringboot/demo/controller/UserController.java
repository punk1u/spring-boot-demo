package tech.punklu.myspringboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.punklu.myspringboot.demo.error.BusinessException;
import tech.punklu.myspringboot.demo.model.User;
import tech.punklu.myspringboot.demo.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "User")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public String test(Model model) {
        List<User> ayUser = userService.findAll();
        model.addAttribute("users", ayUser);
        return "ayUser";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<User> ayUser = userService.findAll();
        model.addAttribute("users", ayUser);
        throw new BusinessException("业务异常");
    }

    @RequestMapping("/findByNameAndPasswordRetry")
    public String findByNameAndPasswordRetry(Model model) {
        User user = userService.findByNameAndPasswordRetry("punklu", "123456");
        model.addAttribute("users", user);
        return "success";
    }


}
