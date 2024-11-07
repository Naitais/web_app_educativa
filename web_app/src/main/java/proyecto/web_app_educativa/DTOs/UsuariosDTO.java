package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.Usuarios;
import java.time.LocalDateTime;

public class UsuariosDTO {
    private int id;
    private Boolean estado;
    private LocalDateTime ultimaSesion;
    private String email;
    private String contraseña;
    private LocalDateTime fechaRegistro;
    private Roles rol;
    private Personas personas;

    public UsuariosDTO(Usuarios usuario){
        this.id = usuario.getId();
        this.estado = usuario.getEstado();
        this.ultimaSesion = usuario.getUltimaSesion();
        this.email = usuario.getEmail();
        this.contraseña = usuario.getContraseña();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.rol = usuario.getRol();
        this.personas = usuario.getPersona();
    }

    public UsuariosDTO(){

    }

    public int getId() {
        return id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public Roles getRol() {
        return rol;
    }

    public Personas getPersonas() {
        return personas;
    }
}
