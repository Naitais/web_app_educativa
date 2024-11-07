package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.UsuariosService;
import java.security.Principal;
@Controller
public class PerfilController {

    private final UsuariosService usuariosService;

    @Autowired
    public PerfilController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/perfil")
    public String perfilTutor(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getPersona() != null) {
                // obtengo el usuario logeado y lo agrego al contexto (model)
                model.addAttribute("usuario", usuario);

                // verifico si la persona cargada en el usuario logeado es una
                // instancia de Tutores
                if (usuario.getPersona() instanceof Tutores) {

                    //si el usuario es un tutor agrego variable de sesion true
                    model.addAttribute("esTutor", true);
                    Tutores tutor = (Tutores) usuario.getPersona();

                    if (tutor.getPerfil() != null) {


                        //agrego al modelo la informacion del perfil del tutor logeado
                        model.addAttribute("perfil", tutor.getPerfil());
                    }

                }else {

                    //caso contrario es false y no podra ver el boton perfil
                    model.addAttribute("esTutor", false);
                }
            }
        }
        return "html/perfil.html";
    }
}
