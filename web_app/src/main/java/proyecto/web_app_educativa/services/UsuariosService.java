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
        return usuariosRepository.findByEstado(UsuarioEstados.ACTIVO).stream()
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
        
        // Validate Password Constraints
        String password = usuariosDTO.getContraseña();
        if (password == null || password.length() < 8 || 
            !password.matches(".*[A-Z].*") || 
            !password.matches(".*[a-z].*") || 
            !password.matches(".*\\d.*") || 
            !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo.");
        }
        
        // Confirm Password Match
        if (!password.equals(usuariosDTO.getConfirmContraseña())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
        
        // Validate age for Tutor
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

        String contraseñaCodificada = passwordEncoder.encode(usuariosDTO.getContraseña());

        // Create Personas object
        proyecto.web_app_educativa.models.Personas nuevaPersona = new proyecto.web_app_educativa.models.Personas(
                usuariosDTO.getNombre(),
                usuariosDTO.getApellido(),
                usuariosDTO.getNumCelular(),
                true // estado activo
        );
        nuevaPersona.setFechaNacimiento(usuariosDTO.getFechaNacimiento());

        Usuarios usuario = new Usuarios(
                usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),
                contraseñaCodificada,
                usuariosDTO.getEstado(),
                usuariosDTO.getRol());
                
        // Link Personas and Usuario
        usuario.setPersona(nuevaPersona);
        
        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(int id, UsuariosDTO usuariosDTO) {

        Usuarios usuario = new Usuarios(
                usuariosDTO.getUltimaSesion(),
                usuariosDTO.getEmail(),
                usuariosDTO.getContraseña(),
                usuariosDTO.getEstado(),
                usuariosDTO.getRol());
        usuario.setId(id);
        return usuariosRepository.save(usuario);
    }

    public Usuarios getUsuarioPorEmail(String email) {
        return usuariosRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No se encontro ningun usuario con el email: " + email));
    }

    // TODO agregar metodo delete pero que haga update
}
