package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.services.TutoriasService;

@RestController
@RequestMapping("/api/tutorias")
public class TutoriasRestController {

    private final TutoriasService tutoriasService;

    @Autowired
    public TutoriasRestController(TutoriasService tutoriasService) {
        this.tutoriasService = tutoriasService;
    }

    @PostMapping("/{perfilId}")
    public ResponseEntity<?> crearTutoria(@PathVariable("perfilId") int perfilId, @RequestBody TutoriasDTO tutoriasDTO) {
        try {
            Tutorias nuevaTutoria = tutoriasService.crearTutoria(tutoriasDTO, perfilId);
            if (nuevaTutoria != null) {
                return ResponseEntity.ok(nuevaTutoria);
            } else {
                return ResponseEntity.badRequest().body("No se pudo crear la tutoría. El perfil puede no existir.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la tutoría: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarTutoria(@PathVariable("id") int id) {
        try {
            Tutorias tutoria = tutoriasService.borrarTutoria(id);
            if(tutoria != null) {
                return ResponseEntity.ok("Tutoría borrada exitosamente.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al borrar la tutoría: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTutoria(@PathVariable("id") int id, @RequestBody TutoriasDTO tutoriasDTO) {
        try {
            Tutorias tutoriaActualizada = tutoriasService.actualizarTutoria(id, tutoriasDTO);
            if (tutoriaActualizada != null) {
                return ResponseEntity.ok(tutoriaActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la tutoría: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<?> toggleEstadoTutoria(@PathVariable("id") int id) {
        try {
            Tutorias tutoria = tutoriasService.toggleEstado(id);
            if(tutoria != null) {
                return ResponseEntity.ok(tutoria);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cambiar el estado de la tutoría: " + e.getMessage());
        }
    }
}
