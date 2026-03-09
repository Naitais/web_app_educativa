package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.SolicitudTutoria;
import proyecto.web_app_educativa.models.Tutorias;

@Repository
public interface SolicitudTutoriaRepository extends JpaRepository<SolicitudTutoria, Integer> {
    boolean existsByAlumnoAndTutoria(Personas alumno, Tutorias tutoria);
}
