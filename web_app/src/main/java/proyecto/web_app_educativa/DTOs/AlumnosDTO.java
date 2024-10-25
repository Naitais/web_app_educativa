package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Alumnos;

public class AlumnosDTO extends PersonasDTO{
    private int id;


    public AlumnosDTO(Alumnos alumno) {
        super(alumno);
        this.id = alumno.getId();
    }

    @Override
    public int getId() {
        return id;
    }
}
