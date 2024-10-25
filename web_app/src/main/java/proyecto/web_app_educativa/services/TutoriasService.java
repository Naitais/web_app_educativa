package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.repositories.TutoriasRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutoriasService {

    private final TutoriasRepository tutoriasRepository;

    @Autowired

    public TutoriasService(TutoriasRepository tutoresRepository) {
        this.tutoriasRepository = tutoresRepository;
    }

    public List<TutoriasDTO> getTutoriasActivas(){
        return tutoriasRepository.findByEstadoTrue().stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    public TutoriasDTO findTutoriaById(int id){
        Tutorias tutorias =  tutoriasRepository.findById(id).orElse(null);
        return new TutoriasDTO(tutorias);
    }

    public Tutorias crearTutoria(TutoriasDTO tutoriaDTO) {
        Tutorias tutoria = new Tutorias(
                tutoriaDTO.getArancel(),tutoriaDTO.getDescripcion(),tutoriaDTO.getDias(),
                tutoriaDTO.getDisciplina(),tutoriaDTO.getEdadMinima(),tutoriaDTO.getEstado(),
                tutoriaDTO.getFechaDesde(),tutoriaDTO.getFechaHasta(),tutoriaDTO.getHorarioDesde(),
                tutoriaDTO.getHorarioHasta(), tutoriaDTO.getMateriales(), tutoriaDTO.getModalidad(),
                tutoriaDTO.getModoPago(), tutoriaDTO.getTipo(),tutoriaDTO.getUbicacion()
        );
        return tutoriasRepository.save(tutoria);
    }

    public Tutorias actualizarTutoria(int id,TutoriasDTO tutoriaDTO){
        Tutorias tutoria = new Tutorias(
                tutoriaDTO.getArancel(),tutoriaDTO.getDescripcion(),tutoriaDTO.getDias(),
                tutoriaDTO.getDisciplina(),tutoriaDTO.getEdadMinima(),tutoriaDTO.getEstado(),
                tutoriaDTO.getFechaDesde(),tutoriaDTO.getFechaHasta(),tutoriaDTO.getHorarioDesde(),
                tutoriaDTO.getHorarioHasta(), tutoriaDTO.getMateriales(), tutoriaDTO.getModalidad(),
                tutoriaDTO.getModoPago(), tutoriaDTO.getTipo(),tutoriaDTO.getUbicacion()
        );
        tutoria.setId(id);
        return tutoriasRepository.save(tutoria);
    }



}
