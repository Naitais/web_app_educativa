package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Alumnos extends Personas{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "tutoria_id")
    private Tutorias tutoria;

    public Alumnos(String nombre, String apellido, int numCelular,  int id) {
        super(nombre, apellido, numCelular);
        nombre = nombre;
        apellido = apellido;
        numCelular = numCelular;

    }

    public Alumnos(String nombre, String apellido, int numCelular) {
        super(nombre, apellido, numCelular);

    }

}
