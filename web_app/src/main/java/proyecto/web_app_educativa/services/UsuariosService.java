package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.repositories.UsuariosRepository;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Usuarios;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    private UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UsuariosService(UsuariosRepository usauriosRepository, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usauriosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuariosDTO> getUsuariosActivos() {
        return usuariosRepository.findByEstadoUsuario(UsuarioEstados.ACTIVO).stream()
                .map(UsuariosDTO::new)
                .collect(Collectors.toList());
    }

    public UsuariosDTO getUsuarioPorId(int id) {
        Usuarios usuario = usuariosRepository.findById(id).orElse(null);
        return new UsuariosDTO(usuario);
    }

    public Usuarios crearUsuario(UsuariosDTO usuariosDTO) {
        // Validate Email
        if (usuariosDTO.getEmail() == null || !usuariosDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email provisto no es válido.");
        }
        
        // reviso dup email
        if (usuariosRepository.findByEmail(usuariosDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ingresado ya se encuentra registrado.");
        }
        
        // valido password
        String password = usuariosDTO.getContraseña();
        if (password == null || password.length() < 8 || 
            !password.matches(".*[A-Z].*") || 
            !password.matches(".*[a-z].*") || 
            !password.matches(".*\\d.*") || 
            !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo.");
        }
        
        //valido password que sean iguales
        if (!password.equals(usuariosDTO.getConfirmContraseña())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
        
        // valido edad tutor
        if (usuariosDTO.getRol() == proyecto.web_app_educativa.models.Roles.ROL_PROFESOR) {
             if (usuariosDTO.getFechaNacimiento() != null) {
                 int age = java.time.Period.between(usuariosDTO.getFechaNacimiento(), java.time.LocalDate.now()).getYears();
                 if (age < 18) {
                     throw new IllegalArgumentException("Un profesor debe ser mayor de 18 años.");
                 }
             } else {
                 throw new IllegalArgumentException("La fecha de nacimiento es requerida para un profesor.");
             }
        }

        // valido que entre la info edl adulto sea obligatoria
        if (usuariosDTO.getRol() == proyecto.web_app_educativa.models.Roles.ROL_ESTUDIANTE && usuariosDTO.getFechaNacimiento() != null) {
            int age = java.time.Period.between(usuariosDTO.getFechaNacimiento(), java.time.LocalDate.now()).getYears();
            if (age < 18) {
                if (usuariosDTO.getNombreAdulto() == null || usuariosDTO.getNombreAdulto().trim().isEmpty()) {
                    throw new IllegalArgumentException("El nombre del adulto responsable es obligatorio para menores de edad.");
                }
                if (usuariosDTO.getNumCelularAdulto() == null || usuariosDTO.getNumCelularAdulto().trim().isEmpty()) {
                    throw new IllegalArgumentException("El número de celular del adulto responsable es obligatorio para menores de edad.");
                }
            }
        }

        String contraseñaCodificada = passwordEncoder.encode(usuariosDTO.getContraseña());

        Usuarios usuario = new Usuarios(
                usuariosDTO.getNombre(),
                usuariosDTO.getApellido(),
                usuariosDTO.getNumCelular(),
                usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),
                contraseñaCodificada,
                usuariosDTO.getEstado(),
                usuariosDTO.getRol());
                
        usuario.setFechaNacimiento(usuariosDTO.getFechaNacimiento());
        usuario.setNombreAdulto(usuariosDTO.getNombreAdulto());
        usuario.setNumCelularAdulto(usuariosDTO.getNumCelularAdulto());
        
        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(int id, UsuariosDTO usuariosDTO) {

        Usuarios usuario = usuariosRepository.findById(id).orElseThrow();
        usuario.setultimaSesion(usuariosDTO.getUltimaSesion());
        usuario.setEmail(usuariosDTO.getEmail());
        usuario.setContraseña(usuariosDTO.getContraseña());
        usuario.setEstadoUsuario(usuariosDTO.getEstado());
        usuario.setRol(usuariosDTO.getRol());
        usuario.setNombre(usuariosDTO.getNombre());
        usuario.setApellido(usuariosDTO.getApellido());
        usuario.setNumCelular(usuariosDTO.getNumCelular());

        return usuariosRepository.save(usuario);
    }

    public Usuarios getUsuarioPorEmail(String email) {
        return usuariosRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No se encontro ningun usuario con el email: " + email));
    }

    // TODO agregar metodo delete pero que haga update
}
