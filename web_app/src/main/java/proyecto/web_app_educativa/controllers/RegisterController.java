package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.services.UsuariosService;

@Controller
public class RegisterController {

    private final UsuariosService usuariosService;

    @Autowired
    public RegisterController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new UsuariosDTO());
        return "html/register.html";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("usuario") UsuariosDTO usuariosDTO, Model model) {
        try {
            // Se le asigna un estado activo por defecto
            usuariosDTO.setEstado(proyecto.web_app_educativa.models.UsuarioEstados.ACTIVO);
            
            // Crea el usuario consumiendo las validaciones del servicio (regex, edad, etc)
            usuariosService.crearUsuario(usuariosDTO);
            
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "html/register.html";
        } catch (Exception e) {
            model.addAttribute("error", "Ha ocurrido un error inesperado durante el registro.");
            return "html/register.html";
        }
    }
}
