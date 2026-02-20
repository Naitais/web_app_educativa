package proyecto.web_app_educativa.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import proyecto.web_app_educativa.models.Personas;

public class PersonasDTO {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellido")
    private String apellido;

    @JsonProperty("num_celular")
    private int numCelular;

    @JsonProperty("estado")
    private Boolean estado;

    @JsonProperty("tutoria_id")
    private Integer tutoriaId;

    public PersonasDTO(Personas persona) {
        this.id = persona.getId();
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.numCelular = persona.getNumCelular();
        this.estado = persona.getEstado();
        if (persona.getTutoria() != null) {
            this.tutoriaId = persona.getTutoria().getId();
        }
    }

    public PersonasDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(int numCelular) {
        this.numCelular = numCelular;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getTutoriaId() {
        return tutoriaId;
    }

    public void setTutoriaId(Integer tutoriaId) {
        this.tutoriaId = tutoriaId;
    }
}
