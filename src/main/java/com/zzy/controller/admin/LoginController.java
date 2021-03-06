package com.zzy.controller.admin;

import com.zzy.entity.User;
import com.zzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ Description
 * @ author zhangziyao
 * @ date 2021/9/6 22:39
 */

@Controller
@RequestMapping("/admin")
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    /**
     * @Description: 跳转登录页面
     * @Return: 返回登录页面
     */
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }
    
    /**
     * @Description: 登录校验
     * @Param: username:用户名
     * @Param: password:密码
     * @Param: session:session域
     * @Param: attributes:返回页面消息
     * @Return: 登录成功跳转登录成功页面，登录失败返回登录页面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if(user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }
    
    /**
     * @Description: 注销
     * @Param: session:session域
     * @Return: 返回登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}

/**
 * @RequestMapping 注解：映射请求路径
 * 登录校验：将前端传递过来的用户名和密码给service进行检验核对，并放入session域中（session是全局的，登录后访问其他页面或者重开页面也是登录状态）
 * 登录成功跳转后台管理页面，失败则跳转登录页面
 * 登录成功后可以进行注销，注销后返回登录页面*/