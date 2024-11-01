package proyecto.web_app_educativa.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.services.UsuariosService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static proyecto.web_app_educativa.models.Roles.ROL_ADMIN;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosController {

    //  todo eliminar lo que tenga que ver con encriptacion de contraseña y login despues de testear
    //private final PasswordEncoder passwordEncoder;
    private UsuariosService usuariosService;

    //public UsuariosController (UsuariosService usuariosService, PasswordEncoder passwordEncoder){
    public UsuariosController (UsuariosService usuariosService){
        this.usuariosService = usuariosService;
        //this.passwordEncoder = passwordEncoder;

    }

    // todo eliminar esto
    /*
    @Bean
    public CommandLineRunner initData(UsuariosRepository usuariosRepository) {
        return (args) -> {
            if(usuariosRepository.findAll().isEmpty()){
                Usuarios usuario = new Usuarios(1,null,"admin@mail", passwordEncoder.encode("admin"),true,ROL_ADMIN);
                usuariosRepository.save(usuario);
            }
        };
    }
*/

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
