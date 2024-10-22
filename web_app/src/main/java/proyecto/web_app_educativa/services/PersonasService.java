package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.repositories.PersonasRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonasService {
    PersonasRepository personasRepository;

    @Autowired
    public PersonasService(PersonasRepository repository){

        this.personasRepository = repository;

    }

    public List<PersonasDTO> getPersonasActivos(){
        return personasRepository.findByEstadoTrue().stream()
                .map(PersonasDTO::new)
                .collect(Collectors.toList());
    }

    public PersonasDTO findPersonaById(Integer id){
        Personas persona =  personasRepository.findById(id).orElse(null);
        return new PersonasDTO(persona);
    }

    public Personas crearPersona(PersonasDTO personaDTO) {
        Personas persona = new Personas(personaDTO.getNombre(), personaDTO.getApellido(), personaDTO.getNumCelular(), personaDTO.getEstado());
        return personasRepository.save(persona);
    }

    public Personas actualizarPersona(Integer id,PersonasDTO personaDTO){
        Personas persona = new Personas(personaDTO.getNombre(), personaDTO.getApellido(), personaDTO.getNumCelular(), personaDTO.getEstado());
        persona.setId(id);
        return personasRepository.save(persona);
    }


}



