package proyecto.web_app_educativa.DTOs;
import proyecto.web_app_educativa.models.Tutores;
import java.util.List;
import java.util.stream.Collectors;

public class TutoresDTO extends PersonasDTO {

    private int id;
    //private Perfiles perfil;
    private List<TutoriasDTO> tutorias;

    public TutoresDTO(Tutores tutor) {
        super(tutor);
        this.id = tutor.getId();
        //this.perfil = tutor.getPerfil();
        this.tutorias = tutor.getTutorias().stream().map(tutoria -> new TutoriasDTO(tutoria)).collect(Collectors.toList());
    }

    public TutoresDTO() {

    }

    @Override
    public int getId() {
        return id;
    }
/*
    public Perfiles getPerfil() {
        return perfil;
    }
*/
    public List<TutoriasDTO> getTutorias() {
        return tutorias;
    }
}
