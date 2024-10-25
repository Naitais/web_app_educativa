package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.models.Usuarios;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    private UsuariosRepository usuariosRepository;

    @Autowired
    UsuariosService (UsuariosRepository usauriosRepository){

        this.usuariosRepository = usauriosRepository;

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
        Usuarios usuario = new Usuarios(usuariosDTO.getId(),usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),usuariosDTO.getContraseña()
        );
        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(int id,UsuariosDTO usuarioDTO){
        Usuarios usuario = new Usuarios(
        );
        usuario.setId(id);
        return usuariosRepository.save(usuario);
    }





}
