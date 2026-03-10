package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Dia;
import proyecto.web_app_educativa.models.DiasDeLaSemana;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Integer> {
    Dia findByNombre(DiasDeLaSemana nombre);
}
