package proyecto.web_app_educativa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {



    @GetMapping("/home")
    //model es un objeto de spring que se utiliza para pasar informacion a la vista actual
    //es como cuando se pasa el contexto

    //principal es un objeto de spring que trae la info del
    //usuario que esta logeado
    public String homePage(Model model, Principal principal) {
        if (principal != null) { //si es distinto a null obtenemos el nombre de usuario
            String username = principal.getName();
            // el atributo del nombre de usuario lo pasamos al "contexto"
            //en caso de que queramos mostrarlo en la pagina
            model.addAttribute("username", username);

        }
        return "html/home.html";
    }
}