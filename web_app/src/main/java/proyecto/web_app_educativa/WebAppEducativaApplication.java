package proyecto.web_app_educativa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.*;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.PersonasRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@SpringBootApplication
public class WebAppEducativaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppEducativaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            UsuariosService usuariosService,
            UsuariosRepository usuariosRepository,
            PersonasRepository personasRepository,
            PerfilesService perfilesService,
            PerfilesRepository perfilesRepository,
            TutoriasService tutoriasService,
            TutoriasRepository tutoriasRepository) {
        return (args) -> {
            /*
            // Create Admin
            if (usuariosRepository.findByEmail("admin").isEmpty()) {
                Usuarios admin = new Usuarios(
                        LocalDateTime.now(),
                        "admin",
                        "admin",
                        UsuarioEstados.ACTIVO,
                        Roles.ROL_ADMIN);
                usuariosService.crearUsuario(new UsuariosDTO(admin));
            }

            // Create Students/Users
            if (usuariosRepository.findByEmail("student").isEmpty()) {
                Usuarios student = new Usuarios(
                        LocalDateTime.now(),
                        "student",
                        "password",
                        UsuarioEstados.ACTIVO,
                        Roles.ROL_ESTUDIANTE);
                usuariosService.crearUsuario(new UsuariosDTO(student));
            }

            // Create Tutors (Personas with ROL_PROFESOR implicit in logic, but explicit in
            // creation if we add it to Personas later, for now just Personas linked to
            // Users or standalone)
            // For simplicity in seeding, we create generic Personas and link them to
            // Perfiles
            if (personasRepository.findAll().isEmpty()) {
                // Tutors
                createTutor(personasRepository, perfilesService, "Pamela", "Mena", 296444444,
                        "Soy una artista apasionada...", "link_foto_pamela", "Arte", 15000);
                createTutor(personasRepository, perfilesService, "Carlos", "Ramirez", 296555555,
                        "Soy profesor de Matemáticas...", "link_foto_carlos", "Matemáticas", 2000);
                // Add more as needed...
            }
            */
        };
    }
/*
    private void createTutor(PersonasRepository personasRepository, PerfilesService perfilesService, String nombre,
            String apellido, int celular, String bio, String foto, String disciplina, double arancel) {
        Personas persona = new Personas(nombre, apellido, celular, true);
        personasRepository.save(persona);

        // Create Profile
        PerfilesDTO perfilDTO = new PerfilesDTO();
        perfilDTO.setBiografia(bio);
        perfilDTO.setFoto(foto);
        perfilDTO.setRating(5.0);
        perfilDTO.setEstado(true);
        perfilDTO.setCertificados(Arrays.asList("Certificado Dummy"));
        perfilDTO.setExperiencia(Arrays.asList("Experiencia Dummy"));

        perfilesService.crearPerfil(perfilDTO, persona.getId());
    }*/
}
