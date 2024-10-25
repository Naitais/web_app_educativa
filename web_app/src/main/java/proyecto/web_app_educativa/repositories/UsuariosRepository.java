package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Usuarios;

import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

    List<Usuarios> findByEstadoTrue();
}

