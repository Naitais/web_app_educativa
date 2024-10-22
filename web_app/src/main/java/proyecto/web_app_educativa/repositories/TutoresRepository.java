package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Tutores;

import java.util.List;

@Repository
public interface TutoresRepository extends JpaRepository<Tutores, Integer> {

    List<Tutores> findByEstadoTrue();
}
