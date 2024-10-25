package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutores extends Personas{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    //private Perfiles perfil;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutorias> tutorias = new ArrayList<Tutorias>();

    public Tutores(String nombre, String apellido, int numCelular, Perfiles perfil) {
        super(nombre, apellido, numCelular);
        nombre = nombre;
        apellido = apellido;
        numCelular = numCelular;

        //this.perfil = perfil;

    }

    public Tutores(String nombre, String apellido, int numCelular){
        super(nombre, apellido, numCelular);

    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
    */

    public List<Tutorias> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<Tutorias> tutorias) {
        this.tutorias = tutorias;
    }
}
