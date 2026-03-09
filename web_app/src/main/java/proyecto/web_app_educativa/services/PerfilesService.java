package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Certificado;
import proyecto.web_app_educativa.models.Experiencia;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.ComentarioPerfil;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.PersonasRepository;
import proyecto.web_app_educativa.repositories.ComentarioPerfilRepository;
import proyecto.web_app_educativa.repositories.ExperienciaRepository;
import proyecto.web_app_educativa.repositories.CertificadoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilesService {

    private PerfilesRepository perfilesRepository;
    private PersonasRepository personasRepository;
    private ComentarioPerfilRepository comentarioPerfilRepository;
    private ExperienciaRepository experienciaRepository;
    private CertificadoRepository certificadoRepository;

    public PerfilesService(PerfilesRepository perfilesRepository,
            PersonasRepository personasRepository,
            ComentarioPerfilRepository comentarioPerfilRepository,
            ExperienciaRepository experienciaRepository,
            CertificadoRepository certificadoRepository) {
        this.perfilesRepository = perfilesRepository;
        this.personasRepository = personasRepository;
        this.comentarioPerfilRepository = comentarioPerfilRepository;
        this.experienciaRepository = experienciaRepository;
        this.certificadoRepository = certificadoRepository;
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

    public Perfiles actualizarPerfil(int id, PerfilesDTO perfilDTO, Experiencia exp, Certificado cert) {
        Perfiles perfil = perfilesRepository.findById(id).orElse(null);
        if (perfil != null) {
            // Update simple fields
            perfil.setBiografia(perfilDTO.getBiografia());
            perfil.setFoto(perfilDTO.getFoto());

            // Append new experience if provided
            if (exp != null && exp.getTitulo() != null && !exp.getTitulo().trim().isEmpty()) {
                perfil.agregarExperiencia(exp);
            }
            // Append new certificate if provided
            if (cert != null && cert.getNombre() != null && !cert.getNombre().trim().isEmpty()) {
                perfil.agregarCertificado(cert);
            }

            return perfilesRepository.save(perfil);
        }
        return null;
    }

    @Transactional
    public void agregarComentario(int perfilId, int personaAutorId, int puntaje, String comentarioTexto) {
        Perfiles perfil = perfilesRepository.findById(perfilId).orElse(null);
        Personas autor = personasRepository.findById(personaAutorId).orElse(null);
        
        if (perfil != null && autor != null) {
            ComentarioPerfil comentario = new ComentarioPerfil(perfil, autor, puntaje, comentarioTexto);
            comentarioPerfilRepository.save(comentario);
            
            perfil.agregarComentario(comentario);
            
            // Perfil ya recalculo su promedio a traves de agregarComentario()
            perfilesRepository.save(perfil);
        }
    }

    // --- Métodos para Experiencia y Certificados ---

    public Experiencia editarExperiencia(int perfilId, Experiencia expModificada) {
        Experiencia exp = experienciaRepository.findById(expModificada.getId()).orElse(null);
        // Validar que exista y que pertenezca al perfil logueado
        if (exp != null && exp.getPerfil() != null && exp.getPerfil().getId() == perfilId) {
            exp.setTitulo(expModificada.getTitulo());
            exp.setInstitucionOEmpresa(expModificada.getInstitucionOEmpresa());
            exp.setFechaDesde(expModificada.getFechaDesde());
            exp.setFechaHasta(expModificada.getFechaHasta());
            exp.setDescripcion(expModificada.getDescripcion());
            return experienciaRepository.save(exp);
        }
        return null;
    }

    @Transactional
    public void eliminarExperiencia(int perfilId, int expId) {
        Experiencia exp = experienciaRepository.findById(expId).orElse(null);
        if (exp != null && exp.getPerfil() != null && exp.getPerfil().getId() == perfilId) {
            // Remove from the profile collection to maintain cache integrity
            exp.getPerfil().getExperiencia().remove(exp);
            experienciaRepository.delete(exp);
        }
    }

    public Certificado editarCertificado(int perfilId, Certificado certModificado) {
        Certificado cert = certificadoRepository.findById(certModificado.getId()).orElse(null);
        if (cert != null && cert.getPerfil() != null && cert.getPerfil().getId() == perfilId) {
            cert.setNombre(certModificado.getNombre());
            cert.setUrlOArchivo(certModificado.getUrlOArchivo());
            return certificadoRepository.save(cert);
        }
        return null;
    }

    @Transactional
    public void eliminarCertificado(int perfilId, int certId) {
        Certificado cert = certificadoRepository.findById(certId).orElse(null);
        if (cert != null && cert.getPerfil() != null && cert.getPerfil().getId() == perfilId) {
            cert.getPerfil().getCertificados().remove(cert);
            certificadoRepository.delete(cert);
        }
    }
}
