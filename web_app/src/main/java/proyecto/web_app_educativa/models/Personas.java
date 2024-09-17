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
    private String email;
    private String contraseña;
    private int num_celular;
    private LocalDate fecha_hora_registro;
    private Boolean estado;

    public Personas(String nombre, String apellido, String email, String contraseña, int num_celular, LocalDate fecha_hora_registro, Boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.num_celular = num_celular;
        this.fecha_hora_registro = LocalDate.now();
        this.estado = true;
    }

    public Personas(){
        this.fecha_hora_registro = LocalDate.now();
        this.estado = true;
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

    public int getNum_celular() {
        return num_celular;
    }

    public void setNum_celular(int num_celular) {
        this.num_celular = num_celular;
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

    public LocalDate getFecha_hora_registro() {
        return fecha_hora_registro;
    }

    public void setFecha_hora_registro(LocalDate fecha_hora_registro) {
        this.fecha_hora_registro = fecha_hora_registro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
