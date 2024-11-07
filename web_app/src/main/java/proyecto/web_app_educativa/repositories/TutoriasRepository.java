package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.models.Tutorias;

import java.util.List;

@Repository
public interface TutoriasRepository extends JpaRepository<Tutorias, Integer> {

    List<Tutorias> findByEstadoTrue();

    //busqueda con query
    @Query("SELECT t FROM Tutorias t WHERE LOWER(t.disciplina) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Tutorias> buscarPorPalabra(@Param("palabra") String palabra);

    //List<Tutorias> findByTutorAndEstadoTrue(Tutores tutor);

}
