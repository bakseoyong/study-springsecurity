package com.example.login;

import com.example.login.VO.LoginForm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class LoginController {
    @Autowired
    private final LoginService loginService;


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("message", "Hello, World!");

        System.out.println("called");

        return "index";
    }

    @GetMapping("/login/form")
    public String goLoginForm(){
        return "login_form";
    }

    @PostMapping("/api/v1/login")
    public String login(@RequestBody LoginForm loginForm){
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        loginService.sendAuthenticationRequest(username, password);
    }
}
