package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutores extends Personas{


    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL)
    private Perfiles perfil;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutorias> tutorias = new ArrayList<Tutorias>();

    public Tutores(String nombre, String apellido, int numCelular, Boolean estado, Perfiles perfil) {
        super(nombre, apellido, numCelular, estado);

        this.perfil = perfil;

    }

    public Tutores(){


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

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public void agregarTutoria(Tutorias tutoria){
        tutoria.setTutor(this);
        tutorias.add(tutoria);
    }

    public void agregarPerfil(Perfiles perfil){
        perfil.setTutor(this);
        setPerfil(perfil);
    }
}
