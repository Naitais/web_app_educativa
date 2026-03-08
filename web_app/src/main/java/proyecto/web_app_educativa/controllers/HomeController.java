package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;
import proyecto.web_app_educativa.services.PerfilesService;

import java.security.Principal;

@Controller
public class HomeController {

    private final UsuariosService usuariosService;
    private final TutoriasService tutoriasService;
    private final PerfilesService perfilesService;

    @Autowired
    public HomeController(
            UsuariosService usuariosService,
            TutoriasService tutoriasService,
            PerfilesService perfilesService) {
        this.usuariosService = usuariosService;
        this.tutoriasService = tutoriasService;
        this.perfilesService = perfilesService;
    }

    @GetMapping("/home")
    // model es un objeto de spring que se utiliza para pasar informacion a la vista
    // actual
    // es como cuando se pasa el contexto

    // principal es un objeto de spring que trae la info del
    // usuario que esta logeado
    public String homePage(
            Model model, 
            Principal principal,
            @org.springframework.web.bind.annotation.RequestParam(value = "q", required = false) String query,
            @org.springframework.web.bind.annotation.RequestParam(value = "searchTutorias", required = false) Boolean searchTutorias,
            @org.springframework.web.bind.annotation.RequestParam(value = "searchPerfiles", required = false) Boolean searchPerfiles) {

        if (principal != null) { // si es distinto a null obtenemos el nombre de usuario
            String email = principal.getName();

            // El email es tambien el nombre de usuario
            // utilizo el servicio para sacar la info del usuario a partir del mail
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                // el atributo del nombre de usuario lo pasamos al "contexto"
                // en caso de que queramos mostrarlo en la pagina

                // informacion de usuario
                model.addAttribute("usuario", usuario.getEmail());
                model.addAttribute("rol", usuario.getRol());
                model.addAttribute("ultimaSesion", usuario.getUltimaSesion());

                // search toggles default to false if not checked
                boolean doSearchTutorias = (searchTutorias != null && searchTutorias);
                boolean doSearchPerfiles = (searchPerfiles != null && searchPerfiles);
                
                // if neither is checked (or simply visiting /home raw), show both / generic feed.
                if(!doSearchTutorias && !doSearchPerfiles) {
                    doSearchTutorias = true;
                    // For default view, showing all active tutorias:
                    if(query == null || query.trim().isEmpty()) {
                         model.addAttribute("tutorias", tutoriasService.getTutoriasActivas());
                    }
                }

                if (query != null && !query.trim().isEmpty()) {
                    model.addAttribute("searchQuery", query);
                    if (doSearchTutorias) {
                        model.addAttribute("tutorias", tutoriasService.buscarTutoriasPorPalabra(query));
                    }
                    if (doSearchPerfiles) {
                        model.addAttribute("perfilesResult", perfilesService.buscarPerfilesPorPalabra(query));
                    }
                } else if (!doSearchTutorias && doSearchPerfiles) {
                   // If they ONLY clicked "Search Tutors" but didn't put a word, we could list all active tutors:
                   model.addAttribute("perfilesResult", perfilesService.getPerfilesActivos());
                }

                // Add flags for Tutor privileges and First-Time Profile Completion
                boolean isTutor = (usuario.getRol() == proyecto.web_app_educativa.models.Roles.ROL_PROFESOR);
                model.addAttribute("esTutor", isTutor);
                
                if (isTutor) {
                    boolean hasProfile = (usuario.getPersona() != null && usuario.getPersona().getPerfil() != null);
                    model.addAttribute("mostrarModalPerfil", !hasProfile);
                }
            }

        }
        // me redirecciona a templates/html/home.html
        return "html/home.html";
    }
}