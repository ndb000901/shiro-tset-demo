package xyz.wuhen.springboot.shiro.demo1.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.HttpResource;
import xyz.wuhen.springboot.shiro.demo1.entity.User;
import xyz.wuhen.springboot.shiro.demo1.service.UserService;
import xyz.wuhen.springboot.shiro.demo1.utils.VerifyCodeUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("user")
public class Usercontroller {

    @Autowired
    private UserService userService;


    @RequestMapping("login")
    public String login(String username,String passwd,String code,HttpSession session) {
        String codes = (String) session.getAttribute("code");
        if (codes.equals(code)) {
            System.out.println("username" + username);
            System.out.println("passwd" + passwd);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(new UsernamePasswordToken(username,passwd));
                return "redirect:/index.jsp";
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("帐号错误");
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误");
            }
        } else {
            throw new RuntimeException("验证码错误");
        }

        return "redirect:/login.jsp";
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }

    @RequestMapping("register")
    public String register(User user) {
        try {
            System.out.println("register--->" + user.getUsername());
            System.out.println("register--->" + user.getPasswd());
            userService.register(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register.jsp";
        }

    }


    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code",code);
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220,60,os,code);
    }
}
