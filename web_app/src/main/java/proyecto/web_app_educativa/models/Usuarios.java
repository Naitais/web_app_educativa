package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

@Entity
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private Boolean estado;
    private LocalDateTime ultimaSesion;
    private String email;
    private String contraseña;
    private LocalDateTime fechaRegistro;
    private Roles rol;

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Personas persona;

    public Usuarios(int id, LocalDateTime ultimaSesion, String email, String contraseña, Boolean estado, Roles rol) {
        this.id = id;
        this.estado = (estado != null) ? estado:true; // si esta null, pongo true sino entra estado
        this.ultimaSesion = (ultimaSesion != null) ? ultimaSesion:LocalDateTime.now();
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = LocalDateTime.now();
        this.rol = rol;
    }

    public Usuarios(){
        //this.estado = (estado != null) ? estado:true; // si esta null, pongo true sino entra estado
        //this.fechaRegistro = LocalDateTime.now();
    }



    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
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

    public void setUltimaSesion(LocalDateTime ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
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
}
