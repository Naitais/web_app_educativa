package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Perfiles;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilesDTO {

    private int id;
    private boolean estado;
    private double rating;
    private String biografia;
    private String foto; //es un link
    private List<String> certificados; //por ahora solo son strings
    private List<String> experiencia;
    private List<TutoriasDTO> tutorias;

    public PerfilesDTO(Perfiles perfil) {
        this.id = perfil.getId();
        this.estado = perfil.getEstado();
        this.rating = perfil.getRating();
        this.biografia = perfil.getBiografia();
        this.foto = perfil.getFoto();
        this.certificados = perfil.getCertificados();
        this.experiencia = perfil.getExperiencia();
        this.tutorias = perfil.getTutorias().stream().map(tutoria -> new TutoriasDTO(tutoria)).collect(Collectors.toList());

    }

    PerfilesDTO(){

    }

    public int getId() {
        return id;
    }

    public boolean getEstado() {
        return estado;
    }

    public double getRating() {
        return rating;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getFoto() {
        return foto;
    }

    public List<String> getCertificados() {
        return certificados;
    }

    public List<String> getExperiencia() {
        return experiencia;
    }

    public List<TutoriasDTO> getTutorias() {
        return tutorias;
    }
}
