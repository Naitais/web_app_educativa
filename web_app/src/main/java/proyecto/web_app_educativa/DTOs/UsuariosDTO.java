package proyecto.web_app_educativa.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Usuarios;
import java.time.LocalDateTime;

public class UsuariosDTO {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @JsonProperty("estado")
    private UsuarioEstados estado;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ultima_sesion")
    private LocalDateTime ultimaSesion;

    @JsonProperty("email")
    private String email;

    @JsonProperty("contrasena")
    private String contraseña;

    @JsonProperty("fecha_registro")
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaRegistro;

    @JsonProperty("rol")
    private Roles rol;

    private Personas personas; // Keep for internal use if needed, but ORDS uses ID

    @JsonProperty("persona_id")
    private Integer personaId;

    public UsuariosDTO(Usuarios usuario) {
        this.id = usuario.getId();
        this.estado = usuario.getEstado();
        this.ultimaSesion = usuario.getUltimaSesion();
        this.email = usuario.getEmail();
        this.contraseña = usuario.getContraseña();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.rol = usuario.getRol();
        this.personas = usuario.getPersona();
        if (usuario.getPersona() != null) {
            this.personaId = usuario.getPersona().getId();
        }
    }

    public UsuariosDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioEstados getEstado() {
        return estado;
    }

    public void setEstado(UsuarioEstados estado) {
        this.estado = estado;
    }

    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(LocalDateTime ultimaSesion) {
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

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
}
