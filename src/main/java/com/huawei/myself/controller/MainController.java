package com.huawei.myself.controller;

import com.huawei.myself.Interceptor.SecurityInterceptor;
import com.huawei.myself.Interceptor.SecurityInterceptorConfig;
import com.huawei.myself.consts.SecurityConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(@SessionAttribute(SecurityConst.SESSION_KEY) String account, Model model) {
        model.addAttribute("name", account);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginPost")
    @ResponseBody
    public Map<String, Object> loginPost(String account, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (!"123456".equals(password)) {
            map.put("success", false);
            map.put("message", "密码错误");
            return map;
        }

        session.setAttribute(SecurityConst.SESSION_KEY, account);
        map.put("success", true);
        map.put("message", "登录成功");
        return map;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SecurityConst.SESSION_KEY);
        return "redirect:/login";
    }

}
