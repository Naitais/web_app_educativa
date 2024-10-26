package proyecto.web_app_educativa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Tutorias {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private int edadMinima;
    //cambiar a LocalDateTime va a ser mas claro y me ahorro tener dos atributos ya que uno
    // la fecha con la hora en uno solo

    private LocalTime horarioDesde;
    private LocalTime horarioHasta;
    private Boolean estado;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String dias;
    private Tipos tipo;
    private String disciplina;
    private String materiales;
    private String ubicacion;
    private String descripcion;
    private Pagos modoPago;
    private Modalidades modalidad;
    private double arancel;

    @OneToMany(mappedBy = "tutoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alumnos> alumnos = new ArrayList<Alumnos>();

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutores tutor;



    public Tutorias(int edadMinima, LocalTime horarioDesde, LocalTime horarioHasta,
                    LocalDate fechaDesde, LocalDate fechaHasta, String dias, Tipos tipo,
                    String disciplina, String materiales, String ubicacion, Boolean estado,
                    String descripcion, Pagos modoPago, Tutores tutor, Modalidades modalidad, double arancel) {


        this.edadMinima = edadMinima;
        this.horarioDesde = horarioDesde;
        this.horarioHasta = horarioHasta;
        this.estado = (estado != null) ? estado:true; // si esta null, pongo true sino entra estado
        this.fechaDesde = LocalDate.now();
        this.fechaHasta = (fechaHasta != null) ? fechaHasta:null; // si esta null, pongo true sino entra estado
        this.dias = dias;
        this.tipo = tipo;
        this.disciplina = disciplina;
        this.materiales = materiales;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.modoPago = modoPago;
        this.tutor = tutor;
        this.modalidad = modalidad;
        this.arancel = arancel;


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

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pagos getModoPago() {
        return modoPago;
    }

    public void setModoPago(Pagos modoPago) {
        this.modoPago = modoPago;
    }

    public Tutores getTutor() {
        return tutor;
    }

    public void setTutor(Tutores tutor) {
        this.tutor = tutor;
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

    public List<Alumnos> getAlumnos() {
        return alumnos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlumnos(List<Alumnos> alumnos) {
        this.alumnos = alumnos;
    }



//este metodo tiene que recibir un string en el siguiente formato 0000000
    // cada 0 en el string representa un dia de la semana, si en la priemra posicion
    // hay un 1 en lugar de un 0, quiere decir que esa tutoria es los lunes
    //y asi para cada dia dependiendo de si hay 0 o 1 representa los dias de la tutoria
    //public void setearDias(List<String> pDiasLista){
    //    for (String dia: pDiasLista){

    //    }
    //}
}
