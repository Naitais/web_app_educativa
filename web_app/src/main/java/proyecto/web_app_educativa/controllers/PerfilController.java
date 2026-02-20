package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.UsuariosService;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.PersonasService;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import java.security.Principal;

@Controller
public class PerfilController {

    private final UsuariosService usuariosService;
    private final PerfilesService perfilesService;
    private final PersonasService personasService;

    @Autowired
    public PerfilController(UsuariosService usuariosService, PerfilesService perfilesService,
            PersonasService personasService) {
        this.usuariosService = usuariosService;
        this.perfilesService = perfilesService;
        this.personasService = personasService;
    }

    @GetMapping("/perfil")
    public String perfilTutor(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                // Check if persona is missing and try to fetch it
                if (usuario.getPersona() == null && usuario.getPersonaId() != null) {
                    PersonasDTO personaDTO = personasService.findPersonaById(usuario.getPersonaId());
                    if (personaDTO != null) {
                        Personas persona = new Personas();
                        persona.setId(personaDTO.getId());
                        persona.setNombre(personaDTO.getNombre());
                        persona.setApellido(personaDTO.getApellido());
                        persona.setNumCelular(personaDTO.getNumCelular());
                        persona.setEstado(personaDTO.getEstado());
                        usuario.setPersona(persona);
                    }
                }

                if (usuario.getPersona() != null) {
                    // obtengo el usuario logeado y lo agrego al contexto (model)
                    model.addAttribute("usuario", usuario);

                    // verifico si la persona cargada en el usuario logeado es una
                    // instancia de Tutores
                    if (usuario.getRol() == Roles.ROL_PROFESOR) {

                        // si el usuario es un tutor agrego variable de sesion true
                        model.addAttribute("esTutor", true);
                        Personas persona = usuario.getPersona();

                        // Fetch profile using service to ensure we check ORDS
                        Object perfil = perfilesService.findPerfilByPersonaId(persona.getId());

                        if (perfil != null) {
                            // agrego al modelo la informacion del perfil del tutor logeado
                            model.addAttribute("perfil", perfil);
                        }

                    } else {

                        // caso contrario es false y no podra ver el boton perfil
                        model.addAttribute("esTutor", false);
                    }
                }
            }
        }
        return "html/perfil.html";
    }
}
