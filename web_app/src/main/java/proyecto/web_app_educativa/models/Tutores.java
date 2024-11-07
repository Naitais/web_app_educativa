package proyecto.web_app_educativa.models;

import jakarta.persistence.*;

@Entity
public class Tutores extends Personas{


    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL)
    private Perfiles perfil;


    public Tutores(String nombre, String apellido, int numCelular, Boolean estado) {
        super(nombre, apellido, numCelular, estado);


    }

    public Tutores(){

    }


    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public void agregarPerfil(Perfiles perfil){
        perfil.setTutor(this);
        setPerfil(perfil);
    }
}
