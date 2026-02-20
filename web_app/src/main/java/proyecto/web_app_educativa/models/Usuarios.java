package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private UsuarioEstados estado;
    private LocalDateTime ultimaSesion;
    private String email;
    private String contraseña;
    private LocalDateTime fechaRegistro;
    private Roles rol;

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @JsonManagedReference
    private Personas persona;

    public Usuarios(
            LocalDateTime ultimaSesion,
            String email,
            String contraseña,
            UsuarioEstados estado,
            Roles rol) {

        this.estado = (estado != null) ? estado : UsuarioEstados.ACTIVO; // default to ACTIVO
        this.ultimaSesion = (ultimaSesion != null) ? ultimaSesion : LocalDateTime.now();
        this.email = email;
        this.contraseña = contraseña;
        this.persona = persona;
        this.fechaRegistro = LocalDateTime.now();
        this.rol = rol;
    }

    public Usuarios() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    @Transient
    private Integer personaId;

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public Integer getPersonaId() {
        if (persona != null) {
            return persona.getId();
        }
        return personaId;
    }
}
