package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ComentarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfiles perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Personas autor;

    private int puntaje; // 1 to 5 stars

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private LocalDateTime fechaCreacion;

    public ComentarioPerfil() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public ComentarioPerfil(Perfiles perfil, Personas autor, int puntaje, String comentario) {
        this();
        this.perfil = perfil;
        this.autor = autor;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public Personas getAutor() {
        return autor;
    }

    public void setAutor(Personas autor) {
        this.autor = autor;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
