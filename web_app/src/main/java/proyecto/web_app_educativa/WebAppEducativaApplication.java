package proyecto.web_app_educativa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.*;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.PersonasService;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.time.LocalDateTime;

@SpringBootApplication
public class WebAppEducativaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppEducativaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            UsuariosService usuariosService,
            PersonasService personasService,
            PerfilesService perfilesService,
            TutoriasService tutoriasService) {
        return (args) -> {
            // Seeding logic is disabled for ORDS migration as we shouldn't auto-seed remote
            // DB on every startup.
            // Use /registro endpoint to create users.

            /*
             * // Example of how it would look with Services if needed:
             * if (usuariosService.getUsuarioPorEmail("admin@admin.com") == null) { // Logic
             * to check existence needed
             * UsuariosDTO admin = new UsuariosDTO();
             * admin.setEmail("admin");
             * admin.setContraseña("admin");
             * admin.setRol(Roles.ROL_ADMIN);
             * admin.setEstado(UsuarioEstados.ACTIVO);
             * usuariosService.crearUsuario(admin);
             * }
             */
        };
    }
}
