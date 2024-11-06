package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.models.Usuarios;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    private UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UsuariosService (UsuariosRepository usauriosRepository, PasswordEncoder passwordEncoder){
        this.usuariosRepository = usauriosRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UsuariosDTO> getUsuariosActivos(){
        return usuariosRepository.findByEstadoTrue().stream()
                .map(UsuariosDTO::new)
                .collect(Collectors.toList());
    }

    public UsuariosDTO findUsuarioById(int id){
        Usuarios usuario =  usuariosRepository.findById(id).orElse(null);
        return new UsuariosDTO(usuario);
    }

    public Usuarios crearUsuario(UsuariosDTO usuariosDTO) {
        String contraseñaCodificada = passwordEncoder.encode(usuariosDTO.getContraseña());

        Usuarios usuario = new Usuarios(
                usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),
                contraseñaCodificada,
                usuariosDTO.getEstado(),
                usuariosDTO.getRol()
        );
        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(int id,UsuariosDTO usuariosDTO){

        Usuarios usuario = new Usuarios(
                usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),
                usuariosDTO.getContraseña(),
                usuariosDTO.getEstado(),
                usuariosDTO.getRol()
        );
        usuario.setId(id);
        return usuariosRepository.save(usuario);
    }





}
