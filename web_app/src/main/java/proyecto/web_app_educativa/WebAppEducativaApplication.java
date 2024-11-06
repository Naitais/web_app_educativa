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

			// creo tutores
			if (tutoresRepository.findAll().isEmpty()) {
				Tutores tutor1 = new Tutores("Pamela", "Mena", 296444444, true);
				Tutores tutor2 = new Tutores("Carlos", "Ramirez", 296555555, true);
				Tutores tutor3 = new Tutores("Lucia", "Martinez", 296666666, true);
				Tutores tutor4 = new Tutores("Santiago", "Fernandez", 296777777, true);
				Tutores tutor5 = new Tutores("Mariana", "Lopez", 296888888, true);
				Tutores tutor6 = new Tutores("Gonzalo", "Perez", 296999999, true);
				Tutores tutor7 = new Tutores("Natalia", "Sanchez", 296111111, true);
				Tutores tutor8 = new Tutores("Esteban", "Garcia", 296222222, true);
				Tutores tutor9 = new Tutores("Victoria", "Jimenez", 296333333, true);
				Tutores tutor10 = new Tutores("Juan", "Castro", 296000000, true);

				tutoresService.crearTutor(new TutoresDTO(tutor1));
				tutoresService.crearTutor(new TutoresDTO(tutor2));
				tutoresService.crearTutor(new TutoresDTO(tutor3));
				tutoresService.crearTutor(new TutoresDTO(tutor4));
				tutoresService.crearTutor(new TutoresDTO(tutor5));
				tutoresService.crearTutor(new TutoresDTO(tutor6));
				tutoresService.crearTutor(new TutoresDTO(tutor7));
				tutoresService.crearTutor(new TutoresDTO(tutor8));
				tutoresService.crearTutor(new TutoresDTO(tutor9));
				tutoresService.crearTutor(new TutoresDTO(tutor10));
			}


			//creo tutorias
			// Create tutorias
			if (tutoriasRepository.findAll().isEmpty()) {
				Tutorias tutoria1 = new Tutorias(10, LocalTime.now(), null, LocalDate.now(), null, "1010100",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR, "Arte", "Materiales incluidos", "Peron", true,
						"Taller de dibujo y pintura LunalaPamPam", TiposPagos.POR_MES, Modalidades.PRESENCIAL, 15000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria1), 1);

				// Additional tutorias
				Tutorias tutoria2 = new Tutorias(12, LocalTime.of(15, 0), null, LocalDate.now(), null, "1100000",
						TiposUbicaciones.A_DOMICILIO, "Matemáticas", "Libros incluidos", "Calle Libertad", true,
						"Clases de Matemáticas", TiposPagos.POR_HORA, Modalidades.VIRTUAL, 2000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria2), 2);

				Tutorias tutoria3 = new Tutorias(15, LocalTime.of(18, 0), null, LocalDate.now(), null, "0011000",
						TiposUbicaciones.A_DOMICILIO, "Inglés", "Sin materiales", "Calle Sucre", true,
						"Clases intensivas de inglés", TiposPagos.POR_CLASE, Modalidades.HIBRIDO, 5000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria3), 3);

				Tutorias tutoria4 = new Tutorias(20, LocalTime.of(9, 0), null, LocalDate.now(), null, "0000011",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR, "Música", "Instrumentos incluidos", "Calle Rivadavia", true,
						"Curso de Guitarra y Piano", TiposPagos.POR_SEMANA, Modalidades.PRESENCIAL, 12000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria4), 4);

				Tutorias tutoria5 = new Tutorias(18, LocalTime.of(16, 0), null, LocalDate.now(), null, "0110001",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR, "Programación", "Laptop y software necesarios", "Calle Corrientes", true,
						"Curso de Java y Python", TiposPagos.POR_MES, Modalidades.VIRTUAL, 25000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria5), 5);

				Tutorias tutoria6 = new Tutorias(14, LocalTime.of(17, 0), null, LocalDate.now(), null, "1000100",
						TiposUbicaciones.A_DOMICILIO, "Historia", "Materiales digitales", "Calle Mitre", true,
						"Taller de historia argentina", TiposPagos.POR_HORA, Modalidades.HIBRIDO, 3000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria6), 6);

				Tutorias tutoria7 = new Tutorias(8, LocalTime.of(14, 0), null, LocalDate.now(), null, "1110000",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR, "Dibujo técnico", "Reglas y papeles incluidos", "Calle Belgrano", true,
						"Clases de dibujo para principiantes", TiposPagos.POR_CLASE, Modalidades.PRESENCIAL, 5000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria7), 7);


				Tutorias tutoria30 = new Tutorias(20, LocalTime.of(10, 0), null, LocalDate.now(), null, "1000001",
						TiposUbicaciones.DOMICILIO_DEL_TUTOR, "Literatura", "Libros de referencia incluidos", "Calle Maipú", true,
						"Curso de literatura moderna", TiposPagos.POR_SEMANA, Modalidades.PRESENCIAL, 18000);
				tutoriasService.crearTutoria(new TutoriasDTO(tutoria30), 10);
			}

		};
	}
}