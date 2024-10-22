package proyecto.web_app_educativa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.List;

public class Tutores extends Personas{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private Perfiles perfil;
    private List<Tutorias> tutorias = new ArrayList<Tutorias>();

    public Tutores(String nombre, String apellido, int numCelular, Boolean estado, Perfiles perfil) {
        super(nombre, apellido, numCelular, estado);
        nombre = nombre;
        apellido = apellido;
        numCelular = numCelular;
        estado = true;
        this.perfil = perfil;

    }

    public Tutores(String nombre, String apellido, int numCelular, Boolean estado){
        super(nombre, apellido, numCelular, estado);
        estado = true;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public List<Tutorias> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<Tutorias> tutorias) {
        this.tutorias = tutorias;
    }
}
