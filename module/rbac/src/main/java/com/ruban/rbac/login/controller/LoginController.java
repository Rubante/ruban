package com.ruban.rbac.login.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ruban.rbac.login.form.LoginForm;
import com.ruban.rbac.service.ILoginService;

@Controller
@RequestMapping(value = "/rbac")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @Valid @ModelAttribute("form") LoginForm loginForm) {

        int result = loginService.login(loginForm);
        if (result == 0) {
            return "main";
        } else {
            model.addAttribute("username",loginForm.getUsername());
            if(result == 1 || result == 2) {
                model.addAttribute("error","用户名或密码不正确");
            } else {
                model.addAttribute("error","用户状态异常，无法登录");
            }
            return "login";
        }
    }
}
