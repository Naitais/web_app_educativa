package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Usuarios;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuariosRepository usuariosRepository;

    @Autowired
    CustomUserDetailsService(UsuariosRepository usauriosRepository) {

        this.usuariosRepository = usauriosRepository;

    }

    // sobreescribe el metodo por default para poder usar un usuario custom
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByEmailAndEstadoUsuario(username, UsuarioEstados.ACTIVO)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContraseña())
                .roles(usuario.getRol().toString())
                .build();
    }
}
