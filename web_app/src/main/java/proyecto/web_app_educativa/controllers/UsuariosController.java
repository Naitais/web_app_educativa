package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.services.UsuariosService;

import java.util.List;

public class UsuariosController {

    private UsuariosService usuariosService;

    public UsuariosController (UsuariosService usuariosService){
        this.usuariosService = usuariosService;

    }


    @GetMapping("")
    public List<UsuariosDTO> listadoUsuarios() {
        return usuariosService.getUsuariosActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable int id){
        try{
            UsuariosDTO tutoria = usuariosService.findUsuarioById(id);
            return new ResponseEntity<>(tutoria, HttpStatus.OK);
        } catch(EntityNotFoundException e){

            return new ResponseEntity<>("Usuario no existe.", HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.CREATED) // para tener una respuesta despues de haber creado el post
    @PostMapping("") //vacio porque asi comparte la misma ruta que los demas
    void crearUsuario(@RequestBody UsuariosDTO usuarioDTO) {
        usuariosService.crearUsuario(usuarioDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void actualizarUsuario(@RequestBody UsuariosDTO usuarioDTO, @PathVariable int id) {
        usuariosService.actualizarUsuario(id, usuarioDTO);
    }


}
