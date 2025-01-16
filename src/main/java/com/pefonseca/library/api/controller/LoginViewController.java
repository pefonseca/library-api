package com.pefonseca.library.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String pageLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String pageHome(Authentication authentication) {
        return "Olá " + authentication.getName();
    }
}
