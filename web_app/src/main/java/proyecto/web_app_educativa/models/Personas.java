package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity

//con esto hago que personas y sus subclases tenga cada una su propia tabla y que personas
//tenga los campos comunos a las subclases pero cada hijo sus campos especificos
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_persona")
public class  Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String nombre;
    private String apellido;
    private int numCelular;
    private Boolean estado;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonBackReference
    private Usuarios usuario;

    public Personas(String nombre, String apellido, int numCelular, Boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numCelular = numCelular;
        this.estado = (estado != null) ? estado:true; // si esta null, pongo true sino entra estado
    }

    public Personas(){

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

    public void agregarUsuario(Usuarios usuario){
        usuario.setPersona(this);
        setUsuario(usuario);
    }

}
