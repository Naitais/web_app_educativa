package proyecto.web_app_educativa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.*;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.PersonasService;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.time.LocalDateTime;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
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
        };
    }
}
