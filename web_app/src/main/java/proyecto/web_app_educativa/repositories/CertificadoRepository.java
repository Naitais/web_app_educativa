package proyecto.web_app_educativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.web_app_educativa.models.Certificado;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Integer> {
}
