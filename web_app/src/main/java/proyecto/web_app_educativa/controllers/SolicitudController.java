package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.repositories.TutoriasRepository;
import proyecto.web_app_educativa.services.SolicitudService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SolicitudController {
    
    private final SolicitudService solicitudService;
    private final TutoriasRepository tutoriasRepository;
    private final UsuariosService usuariosService;

    @Autowired
    public SolicitudController(SolicitudService solicitudService,
                               TutoriasRepository tutoriasRepository,
                               UsuariosService usuariosService) {
        this.solicitudService = solicitudService;
        this.tutoriasRepository = tutoriasRepository;
        this.usuariosService = usuariosService;
    }

    @PostMapping("/solicitudes/crear")
    public String crearSolicitud(@RequestParam("tutoriaId") int tutoriaId, 
                                 Principal principal, 
                                 RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        Usuarios usuario = usuariosService.getUsuarioPorEmail(principal.getName());
        Optional<Tutorias> tutoriaOpt = tutoriasRepository.findById(tutoriaId);
        
        if (tutoriaOpt.isPresent() && usuario != null) {
            Tutorias tutoria = tutoriaOpt.get();
            
            // para que no apliquen a sus mismas tutorias
            if (tutoria.getPerfil() != null && tutoria.getPerfil().getPersona() != null &&
                tutoria.getPerfil().getPersona().getId() == usuario.getId()) {
                redirectAttributes.addFlashAttribute("mensajeError", "No puedes solicitar tu propia tutoría.");
                return "redirect:/home";
            }
            
            // valido la edad para aplicar a tutoria
            if (tutoria.getEdadMinima() > 0 && usuario.getFechaNacimiento() != null) {
                int edad = java.time.Period.between(usuario.getFechaNacimiento(), java.time.LocalDate.now()).getYears();
                if (edad < tutoria.getEdadMinima()) {
                    redirectAttributes.addFlashAttribute("mensajeError", "No cumple con la edad mínima para aplicar a esta tutoría.");
                    return "redirect:/home";
                }
            }
            
            try {
                solicitudService.crearSolicitud(usuario, tutoria);
                redirectAttributes.addFlashAttribute("mensajeExito", "¡Solicitud enviada al tutor exitosamente!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("mensajeError", e.getMessage() != null ? e.getMessage() : "Hubo un error al guardar tu solicitud.");
            }
            
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Hubo un error al procesar tu solicitud.");
        }
        
        return "redirect:/home";
    }

    @PostMapping("/solicitudes/aceptar/{id}")
    public String aceptarSolicitud(@PathVariable("id") int solicitudId, Principal principal, RedirectAttributes redirectAttributes) {
        // En una app real habria que validar que la persona que acepta es el dueño de la tutoria
        // Por ahora lo dejo asi
        boolean exito = solicitudService.aceptarSolicitud(solicitudId);
        if (exito) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Solicitud aceptada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo aceptar la solicitud.");
        }
        return "redirect:/mis-tutorias";
    }

    @PostMapping("/solicitudes/rechazar/{id}")
    public String rechazarSolicitud(@PathVariable("id") int solicitudId, Principal principal, RedirectAttributes redirectAttributes) {
        boolean exito = solicitudService.rechazarSolicitud(solicitudId);
        if (exito) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Solicitud rechazada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo rechazar la solicitud.");
        }
        return "redirect:/mis-tutorias";
    }

    @GetMapping("/mis-solicitudes")
    public String verMisSolicitudes(org.springframework.ui.Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        Usuarios usuario = usuariosService.getUsuarioPorEmail(principal.getName());
        
        if (usuario != null) {
            boolean isTutor = (usuario.getRol() == proyecto.web_app_educativa.models.Roles.ROL_PROFESOR);
            model.addAttribute("esTutor", isTutor);
            model.addAttribute("solicitudes", usuario.getSolicitudes());
        }
        
        return "html/mis-solicitudes.html";
    }
}
