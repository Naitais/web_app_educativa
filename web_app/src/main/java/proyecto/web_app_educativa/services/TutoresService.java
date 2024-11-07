package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.TutoresDTO;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.repositories.TutoresRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutoresService {
    private final TutoresRepository tutoresRepository;

    @Autowired

    public TutoresService(TutoresRepository tutoresRepository) {
        this.tutoresRepository = tutoresRepository;
    }

    public List<TutoresDTO> getTutoresActivos(){
        return tutoresRepository.findByEstadoTrue().stream()
                .map(TutoresDTO::new)
                .collect(Collectors.toList());
    }

    public TutoresDTO findTutorById(int id){
        Tutores tutor =  tutoresRepository.findById(id).orElse(null);
        return new TutoresDTO(tutor);
    }

    public Tutores crearTutor(TutoresDTO tutorDTO) {
        Tutores tutor = new Tutores(
                tutorDTO.getNombre(),
                tutorDTO.getApellido(),
                tutorDTO.getNumCelular(),
                tutorDTO.getEstado(),
                tutorDTO.getPerfil()
        );
        return tutoresRepository.save(tutor);
    }

    public Tutores actualizarTutor(int id,TutoresDTO tutorDTO){
        Tutores tutor = new Tutores(
                tutorDTO.getNombre(),
                tutorDTO.getApellido(),
                tutorDTO.getNumCelular(),
                tutorDTO.getEstado(),
                tutorDTO.getPerfil()
        );
        tutor.setId(id);
        return tutoresRepository.save(tutor);
    }

//TODO agregar metodo delete pero que haga update
}
