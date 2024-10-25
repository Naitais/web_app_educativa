package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import proyecto.web_app_educativa.DTOs.TutoresDTO;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.repositories.TutoriasRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TutoriasService {

    private final TutoriasRepository tutoriasRepository;

    @Autowired

    public TutoresService(TutoriasRepository tutoresRepository) {
        this.tutoriasRepository = tutoresRepository;
    }

    public List<TutoriasDTO> getTutoriasActivas(){
        return tutoriasRepository.findByEstadoTrue().stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    public TutoriasDTO findTutoriaById(Integer id){
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

    public Tutores actualizarTutor(Integer id,TutoresDTO tutorDTO){
        Tutores tutor = new Tutores(tutorDTO.getNombre(), tutorDTO.getApellido(), tutorDTO.getNumCelular(), tutorDTO.getEstado());
        tutor.setId(id);
        return tutoriasRepository.save(tutor);
    }



}
