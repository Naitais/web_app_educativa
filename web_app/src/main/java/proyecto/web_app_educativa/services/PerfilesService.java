package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.repositories.PerfilesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilesService {

    private PerfilesRepository perfilesRepository;

    public PerfilesService(PerfilesRepository perfilesRepository) {
        this.perfilesRepository = perfilesRepository;
    }


    public List<PerfilesDTO> getPerfilesActivos(){
        return perfilesRepository.findByEstadoTrue().stream()
                .map(PerfilesDTO::new)
                .collect(Collectors.toList());
    }

    public PerfilesDTO findPerfilById(int id){
        Perfiles tutor =  perfilesRepository.findById(id).orElse(null);
        return new PerfilesDTO(tutor);
    }

    public Perfiles crearPerfil(PerfilesDTO perfilDTO) {
        // Create a new Perfiles object from the provided DTO
        Perfiles perfil = new Perfiles(

                perfilDTO.getId(),
                perfilDTO.getEstado(),
                perfilDTO.getRating(),
                perfilDTO.getBiografia(),
                perfilDTO.getFoto(),
                perfilDTO.getCertificados(),
                perfilDTO.getExperiencia()

        );

        // Save and return the created 'perfil'
        return perfilesRepository.save(perfil);
    }


    public Perfiles actualizarPerfil(int id,PerfilesDTO tutorDTO){
        Perfiles tutor = new Perfiles(
                tutorDTO.getNombre(),
                tutorDTO.getApellido(),
                tutorDTO.getNumCelular(),
                tutorDTO.getEstado(),
                tutorDTO.getPerfil()
        );
        tutor.setId(id);
        return perfilesRepository.save(tutor);
    }

}
