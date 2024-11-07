package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.services.PerfilesService;

import java.util.List;

@RestController
@RequestMapping("api/perfiles")
public class PerfilesRestController {

        private final PerfilesService perfilesService;

        public PerfilesRestController(PerfilesService perfilesService) {
            this.perfilesService = perfilesService;
        }

        @GetMapping("")
        public List<PerfilesDTO> listadoPerfiles() {
            return perfilesService.getPerfilesActivos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Object> getPerfil(@PathVariable int id){
            try{
                PerfilesDTO perfil = perfilesService.findPerfilById(id);
                return new ResponseEntity<>(perfil, HttpStatus.OK);
            } catch(EntityNotFoundException e){

                return new ResponseEntity<>("Perfil no existe.", HttpStatus.FORBIDDEN);
            }
        }

        @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
        @PostMapping("/{id}") //vacio porque asi comparte la misma ruta que los demas
        void crearPerfil(@RequestBody PerfilesDTO perfilesDTO, @PathVariable int id) {
            perfilesService.crearPerfil(perfilesDTO,id);
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @PutMapping("/{id}")
        void actualizarPerfil(@RequestBody PerfilesDTO perfilesDTO, @PathVariable int id) {
        perfilesService.actualizarPerfil(id, perfilesDTO);
}

}


