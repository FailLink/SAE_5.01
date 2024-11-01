package org.example.sae501serveur.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginTest {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}