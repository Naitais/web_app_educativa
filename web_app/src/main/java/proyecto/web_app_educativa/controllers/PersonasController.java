package proyecto.web_app_educativa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.repositories.PersonasRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/personas")
public class PersonasController {

    private final PersonasRepository personasRepository;

    public PersonasController(PersonasRepository personasRepository) {
        this.personasRepository = personasRepository;
    }

    @GetMapping("")
    public List<PersonasDTO> listadoClientes() {
        return personasRepository.findByEstadoTrue().stream().map(PersonasDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPersona(@PathVariable Integer id){
        Personas persona =  personasRepository.findById(id).orElse(null);
        if (persona == null){
            return new ResponseEntity<>("Persona no existe.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new PersonasDTO(persona), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
    @PostMapping("") //vacio porque asi comparte la misma ruta que los demas
    void crearPersona(@RequestBody PersonasDTO personaDTO) {
        Personas persona = new Personas(personaDTO.getNombre(), personaDTO.getApellido(),personaDTO.getNumCelular(),personaDTO.getEstado());
        personasRepository.save(persona);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void actualizarPersona(@RequestBody PersonasDTO personaDTO, @PathVariable Integer id) {
        Personas persona = new Personas(personaDTO.getNombre(), personaDTO.getApellido(), personaDTO.getNumCelular(), personaDTO.getEstado());
        persona.setId(id);
        personasRepository.save(persona);
    }

}
