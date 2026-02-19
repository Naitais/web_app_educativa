package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private int numCelular;
    private Boolean estado;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonBackReference
    private Usuarios usuario;

    // From Alumnos
    @ManyToOne
    @JoinColumn(name = "tutoria_id")
    private Tutorias tutoria;

    // From Tutores
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonManagedReference // Changed to ManagedReference as Personas is now the parent/owner effectively
                          // in this view or inverse
    private Perfiles perfil;

    public Personas(String nombre, String apellido, int numCelular, Boolean estado) {
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

    public void setNumCelular(int numCelular) {
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

    public int getNumCelular() {
        return numCelular;
    }

    public void setNum_celular(int num_celular) {
        this.numCelular = num_celular;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void agregarUsuario(Usuarios usuario) {
        usuario.setPersona(this);
        setUsuario(usuario);
    }

    public Tutorias getTutoria() {
        return tutoria;
    }

    public void setTutoria(Tutorias tutoria) {
        this.tutoria = tutoria;
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
