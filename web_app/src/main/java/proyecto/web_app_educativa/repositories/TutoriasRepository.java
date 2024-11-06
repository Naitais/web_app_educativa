package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Tutorias;

import java.util.List;

@Repository
public interface TutoriasRepository extends JpaRepository<Tutorias, Integer> {

    List<Tutorias> findByEstadoTrue();

}
