package proyecto.web_app_educativa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.services.TutoriasService;

import java.util.List;

@RestController
@RequestMapping("api/tutorias")
public class TutoriasRestController {

    private final TutoriasService tutoriasService;

    public TutoriasRestController(TutoriasService tutoriasService) {
        this.tutoriasService = tutoriasService;
    }

    @GetMapping("")
    public List<TutoriasDTO> listadoTutorias() {
        return tutoriasService.getTutoriasActivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoriasDTO> getTutoria(@PathVariable int id) {
        TutoriasDTO tutoria = tutoriasService.findTutoriaById(id);
        return (tutoria != null) ? ResponseEntity.ok(tutoria) : ResponseEntity.notFound().build();
    }

    @GetMapping("/busqueda")
    public List<TutoriasDTO> buscar(@RequestParam("palabra") String palabra) {
        return tutoriasService.buscarTutoriasPorPalabra(palabra);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody TutoriasDTO dto, @PathVariable int id) {
        tutoriasService.crearTutoria(dto, id);
    }
}