package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.TutoresDTO;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.repositories.PersonasRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;
import proyecto.web_app_educativa.services.TutoriasService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/tutorias")
public class TutoriasController {


    private final TutoriasService tutoriasService;

    public TutoriasController(TutoriasService tutoriasService) {
        this.tutoriasService = tutoriasService;
    }

    @GetMapping("")
    public List<TutoriasDTO> listadoTutorias() {
        return tutoriasService.getTutoriasActivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTutoria(@PathVariable int id){
        try{
            TutoriasDTO tutoria = tutoriasService.findTutoriaById(id);
            return new ResponseEntity<>(tutoria, HttpStatus.OK);
        } catch(EntityNotFoundException e){

            return new ResponseEntity<>("Tutoria no existe.", HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
    @PostMapping("/{id}") //vacio porque asi comparte la misma ruta que los demas
    void crearTutoria(@RequestBody TutoriasDTO tutoriaDTO, @PathVariable int id) {
        tutoriasService.crearTutoria(tutoriaDTO, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void actualizarTutoria(@RequestBody TutoriasDTO tutoriaDTO, @PathVariable int id) {
        tutoriasService.actualizarTutoria(id, tutoriaDTO);
    }



}
