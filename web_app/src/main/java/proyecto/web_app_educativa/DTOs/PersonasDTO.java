package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Personas;

public class PersonasDTO {

    private int id;
    private String nombre;
    private String apellido;
    private int numCelular;
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

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getNumCelular() {
        return numCelular;
    }

    public Boolean getEstado() {
        return estado;
    }
}
