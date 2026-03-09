package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.ComentarioPerfil;

@Repository
public interface ComentarioPerfilRepository extends JpaRepository<ComentarioPerfil, Integer> {
}
