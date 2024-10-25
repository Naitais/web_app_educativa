package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.services.PersonasService;
import java.util.List;

@RestController
@RequestMapping("api/personas")
public class PersonasController {

    private final PersonasService personasService;

    public PersonasController(PersonasService personasService) {
        this.personasService = personasService;
    }

    @GetMapping("")
    public List<PersonasDTO> listadoPersonas() {
        return personasService.getPersonasActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPersona(@PathVariable int id){
        try{
            PersonasDTO persona = personasService.findPersonaById(id);
            return new ResponseEntity<>(persona, HttpStatus.OK);
        } catch(EntityNotFoundException e){

            return new ResponseEntity<>("Persona no existe.", HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
    @PostMapping("") //vacio porque asi comparte la misma ruta que los demas
    void crearPersona(@RequestBody PersonasDTO personaDTO) {
        personasService.crearPersona(personaDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void actualizarPersona(@RequestBody PersonasDTO personaDTO, @PathVariable int id) {
        personasService.actualizarPersona(id, personaDTO);
    }

}
