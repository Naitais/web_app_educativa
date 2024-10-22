package proyecto.web_app_educativa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
public class Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String nombre;
    private String apellido;
    private int numCelular;
    private Boolean estado;

    public Personas(String nombre, String apellido, int numCelular, Boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numCelular = numCelular;
        this.estado = true;
    }

    public Personas(){
        this.estado = true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}