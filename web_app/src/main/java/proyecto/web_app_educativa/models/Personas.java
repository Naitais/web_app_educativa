package proyecto.web_app_educativa.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String numCelular;
    private Boolean estado;

    private LocalDate fechaNacimiento;
    private String nombreAdulto;

    @Column(name = "num_celular_adulto")
    private String numCelularAdulto;

    // de alumnos
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudTutoria> solicitudes = new ArrayList<>();

    // de tutores
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Perfiles perfil;

    public Personas(String nombre, String apellido, String numCelular, Boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numCelular = numCelular;
        this.estado = (estado != null) ? estado : true;
    }

    public Personas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNum_celular(String num_celular) {
        this.numCelular = num_celular;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreAdulto() {
        return nombreAdulto;
    }

    public void setNombreAdulto(String nombreAdulto) {
        this.nombreAdulto = nombreAdulto;
    }

    public String getNumCelularAdulto() {
        return numCelularAdulto;
    }

    public void setNumCelularAdulto(String numCelularAdulto) {
        this.numCelularAdulto = numCelularAdulto;
    }

    public List<SolicitudTutoria> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudTutoria> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public void agregarPerfil(Perfiles perfil) {
        perfil.setPersona(this);
        setPerfil(perfil);
    }

}
