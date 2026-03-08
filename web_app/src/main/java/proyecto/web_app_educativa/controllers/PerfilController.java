package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Certificado;
import proyecto.web_app_educativa.models.Experiencia;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.UsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;

@Controller
public class PerfilController {

    private final UsuariosService usuariosService;
    private final PerfilesService perfilesService;

    @Autowired
    public PerfilController(UsuariosService usuariosService, PerfilesService perfilesService) {
        this.usuariosService = usuariosService;
        this.perfilesService = perfilesService;
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
                if (usuario.getRol() == Roles.ROL_PROFESOR) {

                    // si el usuario es un tutor agrego variable de sesion true
                    model.addAttribute("esTutor", true);
                    Personas persona = usuario.getPersona();

                    if (persona.getPerfil() != null) {

                        // agrego al modelo la informacion del perfil del tutor logeado
                        model.addAttribute("perfil", persona.getPerfil());
                    }

                } else {

                    // caso contrario es false y no podra ver el boton perfil
                    model.addAttribute("esTutor", false);
                }
            }
        }
        return "html/perfil.html";
    }

    @GetMapping("/perfil/{id}")
    public String perfilTutorPublico(@PathVariable int id, Model model, Principal principal) {
        
        // 1. Fetch the exact public profile DTO securely
        PerfilesDTO perfilPublico = perfilesService.findPerfilById(id);
        
        if (perfilPublico == null) {
            return "redirect:/home"; // fail safe if user queries random ID
        }
        
        model.addAttribute("perfilPublico", perfilPublico);

        // 2. Preserve current user navbar UI state if someone is logged in while browsing this public profile
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                if (usuario.getRol() == Roles.ROL_PROFESOR) {
                    model.addAttribute("esTutor", true);
                } else {
                    model.addAttribute("esTutor", false);
                }
            }
        }
        
        // Return dedicated read-only screen
        return "html/public-perfil.html";
    }

    @GetMapping("/mis-tutorias")
    public String misTutorias(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getPersona() != null && usuario.getRol() == Roles.ROL_PROFESOR) {
                model.addAttribute("usuario", usuario);
                model.addAttribute("esTutor", true);
                Personas persona = usuario.getPersona();
                if (persona.getPerfil() != null) {
                    model.addAttribute("perfil", persona.getPerfil());
                }
                return "html/mis-tutorias.html";
            }
        }
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/primera-vez")
    public String crearPerfilPrimeraVez(
            @RequestParam("biografia") String biografia,
            @RequestParam("foto") String foto,
            @RequestParam(value = "expTitulo", required = false) String expTitulo,
            @RequestParam(value = "expInstitucion", required = false) String expInstitucion,
            @RequestParam(value = "expFechaDesde", required = false) String expFechaDesdeStr,
            @RequestParam(value = "expFechaHasta", required = false) String expFechaHastaStr,
            @RequestParam(value = "expDescripcion", required = false) String expDescripcion,
            @RequestParam(value = "certNombre", required = false) String certNombre,
            @RequestParam(value = "certUrl", required = false) String certUrl,
            Principal principal) {
            
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getPersona() != null && usuario.getRol() == Roles.ROL_PROFESOR) {
                // Prepare DTO
                PerfilesDTO nuevoPerfil = new PerfilesDTO();
                nuevoPerfil.setBiografia(biografia);
                nuevoPerfil.setFoto(foto);
                nuevoPerfil.setEstado(true);
                nuevoPerfil.setRating(0.0);
                
                // Experiencia opcional
                Experiencia exp = null;
                if (expTitulo != null && !expTitulo.trim().isEmpty()) {
                    exp = new Experiencia();
                    exp.setTitulo(expTitulo);
                    exp.setInstitucionOEmpresa(expInstitucion);
                    if (expFechaDesdeStr != null && !expFechaDesdeStr.isEmpty()) {
                        exp.setFechaDesde(java.time.LocalDate.parse(expFechaDesdeStr));
                    }
                    if (expFechaHastaStr != null && !expFechaHastaStr.isEmpty()) {
                        exp.setFechaHasta(java.time.LocalDate.parse(expFechaHastaStr));
                    }
                    exp.setDescripcion(expDescripcion);
                }

                // Certificado opcional
                Certificado cert = null;
                if (certNombre != null && !certNombre.trim().isEmpty()) {
                    cert = new Certificado();
                    cert.setNombre(certNombre);
                    cert.setUrlOArchivo(certUrl);
                }

                // Save using PerfilesService
                perfilesService.crearPerfilConDetalles(nuevoPerfil, usuario.getPersona().getId(), exp, cert);
            }
        }
        return "redirect:/home";
    }
}
