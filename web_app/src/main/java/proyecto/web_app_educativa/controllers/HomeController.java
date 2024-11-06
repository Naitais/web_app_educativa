package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.TutoresService;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final UsuariosService usuariosService;
    private final TutoriasService tutoriasService;
    private final TutoresService tutoresService;

    @Autowired
    public HomeController(
            UsuariosService usuariosService,
            TutoriasService tutoriasService,
            TutoresService tutoresService
    ) {
        this.usuariosService = usuariosService;
        this.tutoriasService = tutoriasService;
        this.tutoresService = tutoresService;
    }


    @GetMapping("/home")
    //model es un objeto de spring que se utiliza para pasar informacion a la vista actual
    //es como cuando se pasa el contexto

    //principal es un objeto de spring que trae la info del
    //usuario que esta logeado
    public String homePage(Model model, Principal principal) {

        if (principal != null) { //si es distinto a null obtenemos el nombre de usuario
            String email = principal.getName();

            //El email es tambien el nombre de usuario
            // utilizo el servicio para sacar la info del usuario a partir del mail
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                // el atributo del nombre de usuario lo pasamos al "contexto"
                // en caso de que queramos mostrarlo en la pagina

                //informacion de usuario
                model.addAttribute("usuario", usuario.getEmail());
                model.addAttribute("rol", usuario.getRol());
                model.addAttribute("ultimaSesion", usuario.getUltimaSesion());

                //informacion de tutorias
                List<TutoriasDTO> availableTutorias = tutoriasService.getTutoriasActivas();
                model.addAttribute("tutorias", availableTutorias);

            }


        }
        //me redirecciona a templates/html/home.html
        return "html/home.html";
    }
}