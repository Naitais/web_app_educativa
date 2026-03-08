package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Tutorias;

import java.util.List;

@Repository
public interface TutoriasRepository extends JpaRepository<Tutorias, Integer> {

    List<Tutorias> findByEstadoTrue();

    // busqueda con query
    @Query("SELECT t FROM Tutorias t LEFT JOIN t.categoria c WHERE t.estado = true AND (LOWER(c.nombre) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(t.ubicacion) LIKE LOWER(CONCAT('%', :palabra, '%')))")
    List<Tutorias> buscarPorPalabra(@Param("palabra") String palabra);

    // List<Tutorias> findByTutorAndEstadoTrue(Tutores tutor);

}
