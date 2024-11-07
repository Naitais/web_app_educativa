package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.TutoresDTO;
import proyecto.web_app_educativa.services.TutoresService;
import java.util.List;

@RestController
@RequestMapping("api/tutores")
public class TutoresRestController {

        private final TutoresService tutoresService;

        public TutoresRestController(TutoresService tutoresService) {
            this.tutoresService = tutoresService;
        }

        @GetMapping("")
        public List<TutoresDTO> listadoTutores() {
            return tutoresService.getTutoresActivos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Object> getTutor(@PathVariable int id){
            try{
                TutoresDTO tutor = tutoresService.findTutorById(id);
                return new ResponseEntity<>(tutor, HttpStatus.OK);
            } catch(EntityNotFoundException e){

                return new ResponseEntity<>("Tutor no existe.", HttpStatus.FORBIDDEN);
            }
        }

        @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
        @PostMapping("") //vacio porque asi comparte la misma ruta que los demas
        void crearTutor(@RequestBody TutoresDTO tutorDTO) {

            tutoresService.crearTutor(tutorDTO);
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @PutMapping("/{id}")
        void actualizarTutor(@RequestBody TutoresDTO tutorDTO, @PathVariable int id) {

            tutoresService.actualizarTutor(id,tutorDTO);
        }

}


