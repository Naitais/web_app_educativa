package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto.web_app_educativa.models.*;
import proyecto.web_app_educativa.repositories.SolicitudTutoriaRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;

import java.util.Optional;

@Service
public class SolicitudService {

    private final SolicitudTutoriaRepository solicitudRepository;
    private final TutoriasRepository tutoriasRepository;

    @Autowired
    public SolicitudService(SolicitudTutoriaRepository solicitudRepository,
                            TutoriasRepository tutoriasRepository) {
        this.solicitudRepository = solicitudRepository;
        this.tutoriasRepository = tutoriasRepository;
    }

    @Transactional
    public void crearSolicitud(Usuarios usuario, Tutorias tutoria) throws Exception {
        //if (!usuario.getRol().equals(Roles.ROL_ESTUDIANTE)){
        //    throw new Exception("Solo usuarios alumnos pueden solicitar tutorias.");
        //}
        if (solicitudRepository.existsByAlumnoAndTutoria(usuario, tutoria)) {
            throw new Exception("Ya enviaste una solicitud para esta tutoría.");
        }
        
        SolicitudTutoria solicitud = new SolicitudTutoria(usuario, tutoria);
        tutoria.getSolicitudes().add(solicitud);
        
        solicitudRepository.save(solicitud);
        tutoriasRepository.save(tutoria);
    }

    @Transactional
    public boolean aceptarSolicitud(int solicitudId) {
        return cambiarEstadoSolicitud(solicitudId, EstadoSolicitud.ACEPTADA);
    }

    @Transactional
    public boolean rechazarSolicitud(int solicitudId) {
        return cambiarEstadoSolicitud(solicitudId, EstadoSolicitud.RECHAZADA);
    }

    private boolean cambiarEstadoSolicitud(int solicitudId, EstadoSolicitud nuevoEstado) {
        Optional<SolicitudTutoria> solicitudOpt = solicitudRepository.findById(solicitudId);
        if (solicitudOpt.isPresent()) {
            SolicitudTutoria solicitud = solicitudOpt.get();
            solicitud.setEstado(nuevoEstado);
            solicitudRepository.save(solicitud);
            return true;
        }
        return false;
    }
}
