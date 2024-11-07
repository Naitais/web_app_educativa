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
    private String dias;
    private TiposUbicaciones tipoUbicaciones;
    private String disciplina;
    private String materiales;
    private String ubicacion;
    private List<AlumnosDTO> alumnos;
    private String descripcion;
    private TiposPagos tipoPago;
    private Perfiles perfil;
    private Modalidades modalidad;
    private double arancel;

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
        this.disciplina = tutoria.getDisciplina();
        this.materiales = tutoria.getMateriales();
        this.ubicacion = tutoria.getUbicacion();
        this.estado = tutoria.getEstado();
        this.descripcion = tutoria.getDescripcion();
        this.tipoPago = tutoria.getTipoPago();
        this.modalidad = tutoria.getModalidad();
        this.arancel = tutoria.getArancel();
        this.alumnos = tutoria.getAlumnos().stream().map(alumno -> new AlumnosDTO(alumno)).collect(Collectors.toList());
    }

    public TutoriasDTO(){

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

    public String getDias() {
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

    public List<AlumnosDTO> getAlumnos() {
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
}
