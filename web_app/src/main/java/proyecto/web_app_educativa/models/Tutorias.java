package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int edadMinima;
    // cambiar a LocalDateTime va a ser mas claro y me ahorro tener dos atributos ya
    // que uno
    // la fecha con la hora en uno solo

    private LocalTime horarioDesde;
    private LocalTime horarioHasta;
    private Boolean estado;
    private LocalDate fechaDesde;
    @ManyToMany
    @JoinTable(
      name = "tutorias_dias", 
      joinColumns = @JoinColumn(name = "tutoria_id"), 
      inverseJoinColumns = @JoinColumn(name = "dia_id")
    )
    private List<Dia> dias = new ArrayList<>();
    
    private TiposUbicaciones tipoUbicaciones;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    private String materiales;
    private String ubicacion;
    private String descripcion;
    private TiposPagos tipoPago;
    private Modalidades modalidad;
    private double valorPorClase;

    @OneToMany(mappedBy = "tutoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudTutoria> solicitudes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonIgnore
    private Perfiles perfil;

    public Tutorias(int edadMinima, LocalTime horarioDesde, LocalTime horarioHasta,
            LocalDate fechaDesde, List<Dia> dias, TiposUbicaciones tipoUbicaciones,
            Categoria categoria, String materiales, String ubicacion, Boolean estado,
            String descripcion, TiposPagos tipoPago, Modalidades modalidad, double valorPorClase) {

        this.edadMinima = edadMinima;
        this.horarioDesde = horarioDesde;
        this.horarioHasta = horarioHasta;
        this.estado = (estado != null) ? estado : true; // si esta null, pongo true sino entra estado
        this.fechaDesde = LocalDate.now();
        this.dias = dias;
        this.tipoUbicaciones = tipoUbicaciones;
        this.categoria = categoria;
        this.materiales = materiales;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.tipoPago = tipoPago;
        this.modalidad = modalidad;
        this.valorPorClase = valorPorClase;

    }

    public Tutorias() {

    }

    public int getId() {
        return id;
    }

    public int getEdadMinima() {
        return edadMinima;
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

    public List<Dia> getDias() {
        return dias;
    }

    public void setDias(List<Dia> dias) {
        this.dias = dias;
    }

    public TiposUbicaciones getTipoUbicaciones() {
        return tipoUbicaciones;
    }

    public void setTipo(TiposUbicaciones tipo) {
        this.tipoUbicaciones = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public void setTipoUbicaciones(TiposUbicaciones tipoUbicaciones) {
        this.tipoUbicaciones = tipoUbicaciones;
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

    public double getValorPorClase() {
        return valorPorClase;
    }

    public void setValorPorClase(double valorPorClase) {
        this.valorPorClase = valorPorClase;
    }

    public List<SolicitudTutoria> getSolicitudes() {
        return solicitudes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSolicitudes(List<SolicitudTutoria> solicitudes) {
        this.solicitudes = solicitudes;
    }

    // este metodo tiene que recibir un string en el siguiente formato 0000000
    // cada 0 en el string representa un dia de la semana, si en la priemra posicion
    // hay un 1 en lugar de un 0, quiere decir que esa tutoria es los lunes
    // y asi para cada dia dependiendo de si hay 0 o 1 representa los dias de la
    // tutoria
    // public void setearDias(List<String> pDiasLista){
    // for (String dia: pDiasLista){

    // }
    // }
}
