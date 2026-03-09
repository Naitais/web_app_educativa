package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TutoriasDTO {

    private int id;
    private int edadMinima;
    private LocalTime horarioDesde;
    private LocalTime horarioHasta;
    private Boolean estado;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private List<String> dias;
    private TiposUbicaciones tipoUbicaciones;
    private String disciplina;
    private String materiales;
    private String ubicacion;
    private List<PersonasDTO> alumnos;
    private String descripcion;
    private TiposPagos tipoPago;
    private Perfiles perfil;
    private Modalidades modalidad;
    private double arancel;
    
    private String tutorNombre;
    private String tutorApellido;
    private double tutorRating;
    private String tutorBiografia;
    private int tutorPerfilId;
    private int tutorPersonaId;

    public TutoriasDTO(Tutorias tutoria) {

        this.id = tutoria.getId();
        this.edadMinima = tutoria.getEdadMinima();
        this.horarioDesde = tutoria.getHorarioDesde();
        this.horarioHasta = tutoria.getHorarioHasta();
        this.estado = tutoria.getEstado();
        this.fechaDesde = tutoria.getFechaDesde();
        this.fechaHasta = tutoria.getFechaHasta();
        this.dias = tutoria.getDias();
        this.tipoUbicaciones = tutoria.getTipoUbicaciones();
        this.disciplina = tutoria.getCategoria() != null ? tutoria.getCategoria().getNombre() : null;
        this.materiales = tutoria.getMateriales();
        this.ubicacion = tutoria.getUbicacion();
        this.estado = tutoria.getEstado();
        this.descripcion = tutoria.getDescripcion();
        this.tipoPago = tutoria.getTipoPago();
        this.modalidad = tutoria.getModalidad();
        this.arancel = tutoria.getValorPorClase();
        this.alumnos = tutoria.getSolicitudes().stream().map(solicitud -> new PersonasDTO(solicitud.getAlumno()))
                .collect(Collectors.toList());
        
        if (tutoria.getPerfil() != null) {
            this.tutorPerfilId = tutoria.getPerfil().getId();
            this.tutorRating = tutoria.getPerfil().getRating();
            this.tutorBiografia = tutoria.getPerfil().getBiografia();
            if (tutoria.getPerfil().getPersona() != null) {
                this.tutorNombre = tutoria.getPerfil().getPersona().getNombre();
                this.tutorApellido = tutoria.getPerfil().getPersona().getApellido();
                this.tutorPersonaId = tutoria.getPerfil().getPersona().getId();
            }
        }
    }

    public TutoriasDTO() {

    }

    public int getId() {
        return id;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public LocalTime getHorarioDesde() {
        return horarioDesde;
    }

    public LocalTime getHorarioHasta() {
        return horarioHasta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public List<String> getDias() {
        return dias;
    }

    public TiposUbicaciones getTipoUbicaciones() {
        return tipoUbicaciones;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getMateriales() {
        return materiales;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public List<PersonasDTO> getAlumnos() {
        return alumnos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TiposPagos getTipoPago() {
        return tipoPago;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public Modalidades getModalidad() {
        return modalidad;
    }

    public double getArancel() {
        return arancel;
    }

    public String getTutorNombre() {
        return tutorNombre;
    }

    public String getTutorApellido() {
        return tutorApellido;
    }

    public double getTutorRating() {
        return tutorRating;
    }

    public String getTutorBiografia() {
        return tutorBiografia;
    }

    public int getTutorPerfilId() {
        return tutorPerfilId;
    }

    public int getTutorPersonaId() {
        return tutorPersonaId;
    }
}
