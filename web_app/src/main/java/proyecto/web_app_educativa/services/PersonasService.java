package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.repositories.PersonasRepository;
import proyecto.web_app_educativa.repositories.UsuariosRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonasService {
    PersonasRepository personasRepository;
    UsuariosRepository usuariosRepository;

    @Autowired
    public PersonasService(PersonasRepository repository,
                           UsuariosRepository usuariosRepository){

        this.personasRepository = repository;
        this.usuariosRepository = usuariosRepository;

    }

    public List<PersonasDTO> getPersonasActivos(){
        return personasRepository.findByEstadoTrue().stream()
                .map(PersonasDTO::new)
                .collect(Collectors.toList());
    }

    public PersonasDTO findPersonaById(int id){
        Personas persona =  personasRepository.findById(id).orElse(null);
        return new PersonasDTO(persona);
    }

    public Personas crearPersona(PersonasDTO personaDTO, int id) {
        Usuarios usuario = usuariosRepository.findById(id).orElse(null);

        Personas persona = new Personas(
                personaDTO.getNombre(),
                personaDTO.getApellido(),
                personaDTO.getNumCelular(),
                personaDTO.getEstado()
        );

        persona.agregarUsuario(usuario);
        return personasRepository.save(persona);
    }

    public Personas actualizarPersona(int id,PersonasDTO personaDTO){
        Personas persona = new Personas(
                personaDTO.getNombre(),
                personaDTO.getApellido(),
                personaDTO.getNumCelular(),
                personaDTO.getEstado()
        );
        persona.setId(id);
        return personasRepository.save(persona);
    }

//TODO agregar metodo delete pero que haga update
}



