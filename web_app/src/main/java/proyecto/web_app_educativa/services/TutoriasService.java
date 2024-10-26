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

    public TutoriasService(TutoriasRepository tutoriasRepository) {
        this.tutoriasRepository = tutoriasRepository;
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


                        tutoriaDTO.getEdadMinima(),
                        tutoriaDTO.getHorarioDesde(),
                        tutoriaDTO.getHorarioHasta(),
                        tutoriaDTO.getFechaDesde(),
                        tutoriaDTO.getFechaHasta(),
                        tutoriaDTO.getDias(),
                        tutoriaDTO.getTipo(),
                        tutoriaDTO.getDisciplina(),
                        tutoriaDTO.getMateriales(),
                        tutoriaDTO.getUbicacion(),
                        tutoriaDTO.getEstado(),
                        tutoriaDTO.getDescripcion(),
                        tutoriaDTO.getModoPago(),
                        tutoriaDTO.getTutor(),
                        tutoriaDTO.getModalidad(),
                        tutoriaDTO.getArancel()

                );


        return tutoriasRepository.save(tutoria);
    }

    public Tutorias actualizarTutoria(int id,TutoriasDTO tutoriaDTO){
        System.out.println("DTO RECIBIDODDDDDDD: " + tutoriaDTO.getEstado());
        Tutorias tutoria = new Tutorias(
                tutoriaDTO.getEdadMinima(),
                tutoriaDTO.getHorarioDesde(),
                tutoriaDTO.getHorarioHasta(),
                tutoriaDTO.getFechaDesde(),
                tutoriaDTO.getFechaHasta(),
                tutoriaDTO.getDias(),
                tutoriaDTO.getTipo(),
                tutoriaDTO.getDisciplina(),
                tutoriaDTO.getMateriales(),
                tutoriaDTO.getUbicacion(),
                tutoriaDTO.getEstado(),
                tutoriaDTO.getDescripcion(),
                tutoriaDTO.getModoPago(),
                tutoriaDTO.getTutor(),
                tutoriaDTO.getModalidad(),
                tutoriaDTO.getArancel()
        );
        tutoria.setId(id);
        return tutoriasRepository.save(tutoria);
    }



}
