package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Experiencia;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Integer> {
}
