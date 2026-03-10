package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SolicitudTutoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Personas alumno;

    @ManyToOne
    @JoinColumn(name = "tutoria_id")
    @JsonIgnore
    private Tutorias tutoria;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;
    
    private LocalDateTime fechaCreacion;

    public SolicitudTutoria() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public SolicitudTutoria(Personas alumno, Tutorias tutoria) {
        this();
        this.alumno = alumno;
        this.tutoria = tutoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Personas getAlumno() {
        return alumno;
    }

    public void setAlumno(Personas alumno) {
        this.alumno = alumno;
    }

    public Tutorias getTutoria() {
        return tutoria;
    }

    public void setTutoria(Tutorias tutoria) {
        this.tutoria = tutoria;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
