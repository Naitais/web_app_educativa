package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Personas;

public class PersonasDTO {

    private int id;
    private String nombre;
    private String apellido;
    private String numCelular;
    private Boolean estado;

    public PersonasDTO(Personas persona) {
        this.id = persona.getId();
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.numCelular = persona.getNumCelular();
        this.estado = persona.getEstado();
    }

    public PersonasDTO(){}

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

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
