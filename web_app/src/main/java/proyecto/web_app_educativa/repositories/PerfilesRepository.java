package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Perfiles;

import java.util.List;

@Repository
public interface PerfilesRepository extends JpaRepository<Perfiles, Integer> {

    List<Perfiles> findByEstadoTrue();

    @Query("SELECT p FROM Perfiles p JOIN p.persona persona WHERE p.estado = true AND (LOWER(p.biografia) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(persona.nombre) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(persona.apellido) LIKE LOWER(CONCAT('%', :palabra, '%')))")
    List<Perfiles> buscarPorPalabra(@Param("palabra") String palabra);
}
