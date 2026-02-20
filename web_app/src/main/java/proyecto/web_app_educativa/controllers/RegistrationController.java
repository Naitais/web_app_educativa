package proyecto.web_app_educativa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.services.PersonasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.time.LocalDateTime;

@Controller
public class RegistrationController {

    private final PersonasService personasService;
    private final UsuariosService usuariosService;

    public RegistrationController(PersonasService personasService, UsuariosService usuariosService) {
        this.personasService = personasService;
        this.usuariosService = usuariosService;
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        return "html/registro";
    }

    @PostMapping("/registro")
    public String registerUser(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam(value = "num_celular", defaultValue = "0") int numCelular,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "rol", defaultValue = "ROL_ESTUDIANTE") String rolStr,
            Model model) {

        try {
            // Step A: Create Persona
            PersonasDTO newPersona = new PersonasDTO();
            newPersona.setNombre(nombre);
            newPersona.setApellido(apellido);
            newPersona.setNumCelular(numCelular);
            newPersona.setEstado(true); // Default active

            PersonasDTO createdPersona = personasService.crearPersona(newPersona);

            if (createdPersona == null || createdPersona.getId() == 0) {
                throw new RuntimeException("Failed to create Persona in ORDS.");
            }

            // Step B: Create Usuario with linked Persona ID
            UsuariosDTO newUser = new UsuariosDTO();
            newUser.setEmail(email);
            newUser.setContraseña(password); // Service handles hashing? Yes, UsuariosService.crearUsuario hashes it.
            newUser.setEstado(UsuarioEstados.ACTIVO);
            newUser.setRol(Roles.valueOf(rolStr));
            // newUser.setUltimaSesion(LocalDateTime.now()); // Let DB handle defaults to
            // avoid format errors
            newUser.setPersonaId(createdPersona.getId());

            usuariosService.crearUsuario(newUser);

            return "redirect:/login"; // Success

        } catch (Exception e) {
            model.addAttribute("error", "Error registering: " + e.getMessage());
            return "html/registro";
        }
    }
}
