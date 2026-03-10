package proyecto.web_app_educativa.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
public class Usuarios extends Personas {

    @Enumerated(EnumType.STRING)
    private UsuarioEstados estadoUsuario;
    private LocalDateTime ultimaSesion;
    private String email;
    private String contraseña;
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    public Usuarios(
            String nombre,
            String apellido,
            String numCelular,
            LocalDateTime ultimaSesion,
            String email,
            String contraseña,
            UsuarioEstados estadoUsuario,
            Roles rol) {
        
        super(nombre, apellido, numCelular, true);
        this.estadoUsuario = (estadoUsuario != null) ? estadoUsuario : UsuarioEstados.ACTIVO; // default to ACTIVO
        this.ultimaSesion = (ultimaSesion != null) ? ultimaSesion : LocalDateTime.now();
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = LocalDateTime.now();
        this.rol = rol;
    }

    public Usuarios() {
        super();
    }

    public UsuarioEstados getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(UsuarioEstados estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    public void setultimaSesion(LocalDateTime ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }
}
