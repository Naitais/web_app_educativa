package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Perfiles;
import java.util.List;

@Repository
public interface PerfilesRepository extends JpaRepository<Perfiles, Integer> {

    List<Perfiles> findByEstadoTrue();
}
