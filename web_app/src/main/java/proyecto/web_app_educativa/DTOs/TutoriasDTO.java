package proyecto.web_app_educativa.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import proyecto.web_app_educativa.models.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TutoriasDTO {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @JsonProperty("edad_minima")
    private int edadMinima;

    @JsonProperty("horario_desde")
    private LocalTime horarioDesde;

    @JsonProperty("horario_hasta")
    private LocalTime horarioHasta;

    private Boolean estado;

    @JsonProperty("fecha_desde")
    private LocalDate fechaDesde;

    @JsonProperty("fecha_hasta")
    private LocalDate fechaHasta;

    @JsonProperty("dias")
    private String dias;

    @JsonProperty("tipo_ubicacion")
    private TiposUbicaciones tipoUbicaciones;

    @JsonProperty("disciplina")
    private String disciplina;

    @JsonProperty("materiales")
    private String materiales;

    @JsonProperty("ubicacion")
    private String ubicacion;

    private List<PersonasDTO> alumnos;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("tipo_pago")
    private TiposPagos tipoPago;

    @JsonProperty("perfil_id")
    private int perfilId;

    private Perfiles perfil;

    @JsonProperty("modalidad")
    private Modalidades modalidad;

    @JsonProperty("arancel")
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
        if (tutoria.getPerfil() != null) {
            this.perfilId = tutoria.getPerfil().getId();
        }
        this.alumnos = tutoria.getAlumnos().stream().map(alumno -> new PersonasDTO(alumno))
                .collect(Collectors.toList());
    }

    public TutoriasDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public LocalTime getHorarioDesde() {
        return horarioDesde;
    }

    public void setHorarioDesde(LocalTime horarioDesde) {
        this.horarioDesde = horarioDesde;
    }

    public LocalTime getHorarioHasta() {
        return horarioHasta;
    }

    public void setHorarioHasta(LocalTime horarioHasta) {
        this.horarioHasta = horarioHasta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public TiposUbicaciones getTipoUbicaciones() {
        return tipoUbicaciones;
    }

    public void setTipoUbicaciones(TiposUbicaciones tipoUbicaciones) {
        this.tipoUbicaciones = tipoUbicaciones;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<PersonasDTO> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<PersonasDTO> alumnos) {
        this.alumnos = alumnos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TiposPagos getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TiposPagos tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public Modalidades getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidades modalidad) {
        this.modalidad = modalidad;
    }

    public double getArancel() {
        return arancel;
    }

    public void setArancel(double arancel) {
        this.arancel = arancel;
    }
}
