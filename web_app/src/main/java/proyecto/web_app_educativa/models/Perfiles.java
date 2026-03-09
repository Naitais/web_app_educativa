package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Perfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean estado;
    private double rating;
    private String biografia;
    private String foto; // es un link
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificado> certificados = new ArrayList<>();
    
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experiencia> experiencia = new ArrayList<>();

    // si no lo pongo para que traiga todo de una con eager me tira error hibernate
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutorias> tutorias = new ArrayList<>();
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioPerfil> comentarios = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @JsonBackReference
    // @JsonIgnore //TODO tuve que incluir jsonignore preguntar al profe sobre la
    // recursion
    private Personas persona;

    public Perfiles(
            Boolean estado,
            double rating,
            String biografia,
            String foto) {

        this.rating = rating;
        this.biografia = biografia;
        this.foto = foto;
        this.estado = (estado != null) ? estado : true; // si esta null, pongo true sino entra estado
    }

    public Perfiles() {
    }

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

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
    }

    public List<Experiencia> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<Experiencia> experiencia) {
        this.experiencia = experiencia;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public List<Tutorias> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<Tutorias> tutorias) {
        this.tutorias = tutorias;
    }

    public void agregarTutoria(Tutorias tutoria) {
        tutoria.setPerfil(this);
        tutorias.add(tutoria);
    }

    public void agregarCertificado(Certificado certificado) {
        certificado.setPerfil(this);
        certificados.add(certificado);
    }

    public void agregarExperiencia(Experiencia exp) {
        exp.setPerfil(this);
        experiencia.add(exp);
    }

    public List<ComentarioPerfil> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioPerfil> comentarios) {
        this.comentarios = comentarios;
    }

    public void agregarComentario(ComentarioPerfil comentario) {
        comentario.setPerfil(this);
        comentarios.add(comentario);
        actualizarPromedioRating();
    }

    public void actualizarPromedioRating() {
        if (comentarios == null || comentarios.isEmpty()) {
            this.rating = 0.0;
            return;
        }
        
        double sum = 0;
        int totalCom = comentarios.size();

        for (ComentarioPerfil cp : comentarios) {
            sum += cp.getPuntaje();
        }

        this.rating = sum / totalCom;
    }
}
