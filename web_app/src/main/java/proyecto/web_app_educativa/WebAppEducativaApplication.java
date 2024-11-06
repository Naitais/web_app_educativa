package proyecto.web_app_educativa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import proyecto.web_app_educativa.DTOs.TutoresDTO;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.*;
import proyecto.web_app_educativa.repositories.TutoresRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.services.TutoresService;
import proyecto.web_app_educativa.services.TutoriasService;
import proyecto.web_app_educativa.services.UsuariosService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class WebAppEducativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppEducativaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			UsuariosService usuariosService,
			UsuariosRepository usuariosRepository,
			TutoresService tutoresService,
			TutoresRepository tutoresRepository,
			TutoriasService tutoriasService,
			TutoriasRepository tutoriasRepository
			) {
		return (args) -> {

			//creo usuarios
			if (usuariosRepository.findAll().isEmpty()) {
				Usuarios usuarios1 = new Usuarios(
						LocalDateTime.now(),
						"admin",
						"admin",
						true,
						Roles.ROL_ADMIN);

				UsuariosDTO usuariosDTO1 = new UsuariosDTO(usuarios1);
				usuariosService.crearUsuario(usuariosDTO1);

				Usuarios usuarios2 = new Usuarios(
						LocalDateTime.now(),
						"pame",
						"pame",
						true,
						Roles.ROL_USUARIO);

				UsuariosDTO usuariosDTO2 = new UsuariosDTO(usuarios2);
				usuariosService.crearUsuario(usuariosDTO2);

			}

			//creo tutores
			if (tutoresRepository.findAll().isEmpty()) {
				Tutores tutor1 = new Tutores(
						"Pamela",
						"Mena",
						296444444,
						true);

				TutoresDTO tutoresDTO1 = new TutoresDTO(tutor1);
				tutoresService.crearTutor(tutoresDTO1);

			}

			//creo tutorias
			if (tutoriasRepository.findAll().isEmpty()) {

				Tutorias tutoria1 = new Tutorias(
						10,
						LocalTime.now(),
						null,
						LocalDate.now(),
						null,
						"1010100",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR,
						"Arte",
						"Materiales incluidos",
						"Peron",
						true,
						"Taller de dibujo y pintura LunalaPamPam",
						TiposPagos.POR_MES,
						Modalidades.PRESENCIAL,
						15000
				);

				TutoriasDTO tutoriasDTO1 = new TutoriasDTO(tutoria1);
				tutoriasService.crearTutoria(tutoriasDTO1,1);

			}
		};
	}
}