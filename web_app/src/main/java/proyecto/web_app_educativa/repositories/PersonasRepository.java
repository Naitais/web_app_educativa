package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Personas;
import java.util.List;

@Repository
public interface PersonasRepository extends JpaRepository<Personas, Integer> {

    List <Personas> findByEstadoTrue();

}
