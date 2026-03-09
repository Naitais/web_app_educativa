package proyecto.web_app_educativa.DTOs;

import java.time.LocalDateTime;

public class ComentarioPerfilDTO {
    private int id;
    private String autorNombre;
    private int puntaje;
    private String comentario;
    private LocalDateTime fechaCreacion;

    public ComentarioPerfilDTO() {}

    public ComentarioPerfilDTO(int id, String autorNombre, int puntaje, String comentario, LocalDateTime fechaCreacion) {
        this.id = id;
        this.autorNombre = autorNombre;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
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
