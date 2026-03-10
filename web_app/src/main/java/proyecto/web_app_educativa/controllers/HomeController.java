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
    private final proyecto.web_app_educativa.repositories.CategoriaRepository categoriaRepository;

    @Autowired
    public HomeController(
            UsuariosService usuariosService,
            TutoriasService tutoriasService,
            PerfilesService perfilesService,
            proyecto.web_app_educativa.repositories.CategoriaRepository categoriaRepository) {
        this.usuariosService = usuariosService;
        this.tutoriasService = tutoriasService;
        this.perfilesService = perfilesService;
        this.categoriaRepository = categoriaRepository;
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
            @org.springframework.web.bind.annotation.RequestParam(value = "modalidad", required = false) proyecto.web_app_educativa.models.Modalidades modalidad,
            @org.springframework.web.bind.annotation.RequestParam(value = "precioMax", required = false) Double precioMax,
            @org.springframework.web.bind.annotation.RequestParam(value = "edadMinima", required = false) Integer edadMinima,
            @org.springframework.web.bind.annotation.RequestParam(value = "horarioDesde", required = false) java.time.LocalTime horarioDesde,
            @org.springframework.web.bind.annotation.RequestParam(value = "horarioHasta", required = false) java.time.LocalTime horarioHasta,
            @org.springframework.web.bind.annotation.RequestParam(value = "tipoPago", required = false) proyecto.web_app_educativa.models.TiposPagos tipoPago,
            @org.springframework.web.bind.annotation.RequestParam(value = "tipoUbicacion", required = false) proyecto.web_app_educativa.models.TiposUbicaciones tipoUbicacion,
            @org.springframework.web.bind.annotation.RequestParam(value = "categoriaId", required = false) Integer categoriaId) {
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

                boolean hasQuery = (query != null && !query.trim().isEmpty());
                boolean hasModalidad = (modalidad != null);
                boolean hasPrecioMax = (precioMax != null);
                boolean hasExtraFilters = (edadMinima != null || horarioDesde != null || horarioHasta != null || tipoPago != null || tipoUbicacion != null || categoriaId != null);

                if (hasQuery || hasModalidad || hasPrecioMax || hasExtraFilters) {
                    if (hasQuery) {
                        model.addAttribute("searchQuery", query);
                    }
                    model.addAttribute("tutorias", tutoriasService.buscarTutoriasAvanzado(query, modalidad, precioMax, edadMinima, horarioDesde, horarioHasta, tipoPago, tipoUbicacion, categoriaId));
                } else {
                    model.addAttribute("tutorias", tutoriasService.getTutoriasActivas());
                }
                
                // agrego la categoria al contexto
                model.addAttribute("categorias", categoriaRepository.findAll());
                // agrego el id para verificar que no aplique a su propia tutoria
                model.addAttribute("currentUsuarioPersonaId", usuario != null ? usuario.getId() : null);

                // armo lista de tutorias ya solicitadas
                java.util.List<Integer> tutoriasSolicitadasIds = new java.util.ArrayList<>();
                if (usuario.getSolicitudes() != null) {
                    for (proyecto.web_app_educativa.models.SolicitudTutoria sol : usuario.getSolicitudes()) {
                        tutoriasSolicitadasIds.add(sol.getTutoria().getId());
                    }
                }
                model.addAttribute("tutoriasSolicitadasIds", tutoriasSolicitadasIds);

                // agego bandero de tutor y si es la primera vez que inicia
                boolean isTutor = (usuario.getRol() == proyecto.web_app_educativa.models.Roles.ROL_PROFESOR);
                model.addAttribute("esTutor", isTutor);
                
                if (isTutor) {
                    boolean hasProfile = (usuario.getPerfil() != null);
                    model.addAttribute("mostrarModalPerfil", !hasProfile);
                }
            }

        }
        // me redirecciona a templates/html/home.html
        return "html/home.html";
    }
}