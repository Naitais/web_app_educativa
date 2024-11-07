package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Perfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private boolean estado;
    private double rating;
    private String biografia;
    private String foto; //es un link
    @ElementCollection
    private List<String> certificados; //por ahora solo son strings
    @ElementCollection
    private List<String> experiencia;


    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutorias> tutorias = new ArrayList<>();
    //private List<Reseñas> reseñas;

    @OneToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    private Tutores tutor;

    public Perfiles(
            Boolean estado,
            double rating,
            String biografia,
            String foto,
            List<String> certificados,
            List<String> experiencia
    ) {


        this.rating = rating;
        this.biografia = biografia;
        this.foto = foto;
        this.certificados = certificados;
        this.experiencia = experiencia;
        this.estado = (estado != null) ? estado:true; // si esta null, pongo true sino entra estado
    }

    public Perfiles(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<String> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<String> certificados) {
        this.certificados = certificados;
    }

    public List<String> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<String> experiencia) {
        this.experiencia = experiencia;
    }

    public Tutores getTutor() {
        return tutor;
    }

    public void setTutor(Tutores tutor) {
        this.tutor = tutor;
    }


    public List<Tutorias> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<Tutorias> tutorias) {
        this.tutorias = tutorias;
    }

    public void agregarTutoria(Tutorias tutoria){
        tutoria.setPerfil(this);
        tutorias.add(tutoria);
    }

}
