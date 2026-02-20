package proyecto.web_app_educativa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.security.Principal;

@Controller
public class PerfilRegistrationController {

    private final PerfilesService perfilesService;
    private final UsuariosService usuariosService;

    public PerfilRegistrationController(PerfilesService perfilesService, UsuariosService usuariosService) {
        this.perfilesService = perfilesService;
        this.usuariosService = usuariosService;
    }

    @GetMapping("/crear-perfil")
    public String showCreateProfileForm(Model model, Principal principal) {
        if (principal == null)
            return "redirect:/login";
        return "html/crear_perfil";
    }

    @PostMapping("/crear-perfil")
    public String createProfile(
            @RequestParam("biografia") String biografia,
            @RequestParam("foto") String foto,
            @RequestParam(value = "certificados", required = false) String certificados, // simple comma separated logic
                                                                                         // for now?
            @RequestParam(value = "experiencia", required = false) String experiencia,
            Principal principal,
            Model model) {

        if (principal == null)
            return "redirect:/login";

        try {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario == null)
                throw new RuntimeException("Usuario no encontrado.");

            int personaId = 0;
            if (usuario.getPersonaId() != null) {
                personaId = usuario.getPersonaId();
            } else if (usuario.getPersona() != null) {
                personaId = usuario.getPersona().getId();
            } else {
                throw new RuntimeException("Usuario no tiene persona asignada.");
            }

            PerfilesDTO perfilDTO = new PerfilesDTO();
            perfilDTO.setBiografia(biografia);
            perfilDTO.setFoto(foto); // URL
            perfilDTO.setEstado(true);
            perfilDTO.setRating(5.0); // Start with 5 stars? Or 0?

            // Handle lists if needed, for simplicity we might skip or assume empty list for
            // now if UI is simple
            perfilDTO.setCertificados(certificados != null ? certificados : "");
            perfilDTO.setExperiencia(experiencia != null ? experiencia : "");

            perfilesService.crearPerfil(perfilDTO, personaId);

            return "redirect:/home"; // Success, now home should show content

        } catch (Exception e) {
            model.addAttribute("error", "Error creating profile: " + e.getMessage());
            return "html/crear_perfil";
        }
    }
}
