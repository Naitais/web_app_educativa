package proyecto.web_app_educativa.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Profesores extends Personas{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private double arancel;
    private Modalidades modalidad;
    private Tipos tipo;
    private List<LocalTime> horarios;
    private List<DiasDeLaSemana> dias;
    private String ubicacion;
    private double rating_promedio;
    private String certificado;
    private List<String> contenido_disciplinar;

    public Profesores(String nombre, String apellido, String email, String contraseña, int num_celular, LocalDate fecha_hora_registro, Boolean estado, double arancel, Modalidades modalidad, Tipos tipo, List<LocalTime> horarios, List<DiasDeLaSemana> dias, String ubicacion, double rating_promedio, String certificado, List<String> contenido_disciplinar) {
        super(nombre, apellido, email, contraseña, num_celular, fecha_hora_registro, estado);
        this.arancel = arancel;
        this.modalidad = modalidad;
        this.tipo = tipo;
        this.horarios = horarios;
        this.dias = dias;
        this.ubicacion = ubicacion;
        this.rating_promedio = rating_promedio;
        this.certificado = certificado;
        this.contenido_disciplinar = contenido_disciplinar;
    }

    public Profesores(){

    }
}
