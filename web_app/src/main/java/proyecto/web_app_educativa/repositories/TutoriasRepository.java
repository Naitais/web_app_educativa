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

    // busqueda avanzada con varios filtros
    // busqueda avanzada con varios filtros
    @Query("SELECT t FROM Tutorias t LEFT JOIN t.categoria c WHERE t.estado = true AND " +
           "(:palabra IS NULL OR :palabra = '' OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(t.ubicacion) LIKE LOWER(CONCAT('%', :palabra, '%'))) AND " +
           "(:modalidad IS NULL OR t.modalidad = :modalidad) AND " +
           "(:precioMax IS NULL OR t.valorPorClase <= :precioMax) AND " +
           "(:edadMinima IS NULL OR t.edadMinima <= :edadMinima) AND " +
           "(:horarioDesde IS NULL OR t.horarioDesde >= :horarioDesde) AND " +
           "(:horarioHasta IS NULL OR t.horarioHasta <= :horarioHasta) AND " +
           "(:tipoPago IS NULL OR t.tipoPago = :tipoPago) AND " +
           "(:tipoUbicacion IS NULL OR t.tipoUbicaciones = :tipoUbicacion) AND " +
           "(:categoriaId IS NULL OR (c IS NOT NULL AND c.id = :categoriaId))")
    List<Tutorias> buscarAvanzado(
            @Param("palabra") String palabra, 
            @Param("modalidad") proyecto.web_app_educativa.models.Modalidades modalidad, 
            @Param("precioMax") Double precioMax,
            @Param("edadMinima") Integer edadMinima,
            @Param("horarioDesde") java.time.LocalTime horarioDesde,
            @Param("horarioHasta") java.time.LocalTime horarioHasta,
            @Param("tipoPago") proyecto.web_app_educativa.models.TiposPagos tipoPago,
            @Param("tipoUbicacion") proyecto.web_app_educativa.models.TiposUbicaciones tipoUbicacion,
            @Param("categoriaId") Integer categoriaId);
    // List<Tutorias> findByTutorAndEstadoTrue(Tutores tutor);

}
