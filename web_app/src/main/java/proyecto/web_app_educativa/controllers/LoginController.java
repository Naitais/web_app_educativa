package proyecto.web_app_educativa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "html/login.html"; // con esto apunto la ruta al html para el login
    }
}
