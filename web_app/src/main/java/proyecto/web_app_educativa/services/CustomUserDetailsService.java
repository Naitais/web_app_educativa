package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Usuarios;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuariosService usuariosService;

    @Autowired
    CustomUserDetailsService(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosService.getUsuarioPorEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        if (usuario.getEstado() != UsuarioEstados.ACTIVO) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContraseña())
                .roles(usuario.getRol().toString())
                .build();
    }
}
