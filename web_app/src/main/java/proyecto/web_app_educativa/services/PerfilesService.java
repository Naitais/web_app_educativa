package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Certificado;
import proyecto.web_app_educativa.models.Experiencia;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.PersonasRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilesService {

    private PerfilesRepository perfilesRepository;
    private PersonasRepository personasRepository;

    public PerfilesService(PerfilesRepository perfilesRepository,
            PersonasRepository personasRepository) {
        this.perfilesRepository = perfilesRepository;
        this.personasRepository = personasRepository;
    }

    public List<PerfilesDTO> getPerfilesActivos() {
        return perfilesRepository.findByEstadoTrue().stream()
                .map(PerfilesDTO::new)
                .collect(Collectors.toList());
    }

    public PerfilesDTO findPerfilById(int id) {
        Perfiles tutor = perfilesRepository.findById(id).orElse(null);
        return new PerfilesDTO(tutor);
    }

    public List<PerfilesDTO> buscarPerfilesPorPalabra(String palabra) {
        List<Perfiles> perfiles = perfilesRepository.buscarPorPalabra(palabra);
        return perfiles.stream()
                .map(PerfilesDTO::new)
                .collect(Collectors.toList());
    }

    public Perfiles crearPerfil(PerfilesDTO perfilDTO, int id) {
        Personas persona = personasRepository.findById(id).orElse(null);

        if (persona != null) {
            Perfiles perfil = new Perfiles(
                    perfilDTO.getEstado(),
                    perfilDTO.getRating(),
                    perfilDTO.getBiografia(),
                    perfilDTO.getFoto()

            );

            persona.agregarPerfil(perfil);
            return perfilesRepository.save(perfil);
        }
        return null; // Handle null implicitly or throw exception
    }

    public Perfiles crearPerfilConDetalles(PerfilesDTO perfilDTO, int id, Experiencia exp, Certificado cert) {
        Personas persona = personasRepository.findById(id).orElse(null);

        if (persona != null) {
            Perfiles perfil = new Perfiles(
                    perfilDTO.getEstado(),
                    perfilDTO.getRating(),
                    perfilDTO.getBiografia(),
                    perfilDTO.getFoto()
            );

            if (exp != null) {
                perfil.agregarExperiencia(exp);
            }
            
            if (cert != null) {
                perfil.agregarCertificado(cert);
            }

            persona.agregarPerfil(perfil);
            return perfilesRepository.save(perfil);
        }
        return null;
    }

    public Perfiles actualizarPerfil(int id, PerfilesDTO perfilDTO) {
        Perfiles perfil = new Perfiles(
                perfilDTO.getEstado(),
                perfilDTO.getRating(),
                perfilDTO.getBiografia(),
                perfilDTO.getFoto()

        );
        perfil.setId(id);
        return perfilesRepository.save(perfil);
    }

}
