package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.TutoresRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilesService {

    private PerfilesRepository perfilesRepository;
    private TutoresRepository tutoresRepository;

    public PerfilesService(PerfilesRepository perfilesRepository,
                           TutoresRepository tutoresRepository) {
        this.perfilesRepository = perfilesRepository;
        this.tutoresRepository = tutoresRepository;
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

    public Perfiles crearPerfil(PerfilesDTO perfilDTO, int id) {
        Tutores tutor = tutoresRepository.findById(id).orElse(null);

        Perfiles perfil = new Perfiles(
                perfilDTO.getEstado(),
                perfilDTO.getRating(),
                perfilDTO.getBiografia(),
                perfilDTO.getFoto(),
                perfilDTO.getCertificados(),
                perfilDTO.getExperiencia()

        );

        tutor.agregarPerfil(perfil);
        return perfilesRepository.save(perfil);
    }


    public Perfiles actualizarPerfil(int id,PerfilesDTO perfilDTO){
        Perfiles perfil = new Perfiles(
                perfilDTO.getEstado(),
                perfilDTO.getRating(),
                perfilDTO.getBiografia(),
                perfilDTO.getFoto(),
                perfilDTO.getCertificados(),
                perfilDTO.getExperiencia()

        );
        perfil.setId(id);
        return perfilesRepository.save(perfil);
    }



}
