package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.models.Usuarios;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UsuariosRepository usuariosRepository;

    @Autowired
    CustomUserDetailsService (UsuariosRepository usauriosRepository){

        this.usuariosRepository = usauriosRepository;

    }

    //sobreescribe el metodo por default para poder usar un usuario custom
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByUsernameAndEstado(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getContraseña(),
                //ademas de los parametros usuario y contraseña de arriba, se pasan estos otros
                //si cualquiera de estos es false entonces no pasa el usuario
                true, // la cuenta esta habilitada
                true, // la cuenta no expiro
                true, // las credenciales no expiraron
                true, // la cuenta no esta bloqueada
                List.of(new SimpleGrantedAuthority("ROL_USUARIO")) // asigna el rol predeterminado
        );
    }


}
