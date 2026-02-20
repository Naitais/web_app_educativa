package proyecto.web_app_educativa.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import proyecto.web_app_educativa.models.Perfiles;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilesDTO {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @JsonProperty("estado")
    private boolean estado;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("biografia")
    private String biografia;

    @JsonProperty("foto")
    private String foto; // es un link

    private String certificados; // Changed to String for ORDS CLOB
    private String experiencia; // Changed to String for ORDS CLOB

    private List<TutoriasDTO> tutorias;

    @JsonProperty("persona_id")
    private Integer personaId;

    public PerfilesDTO(Perfiles perfil) {
        this.id = perfil.getId();
        this.estado = perfil.getEstado();
        this.rating = perfil.getRating();
        this.biografia = perfil.getBiografia();
        this.foto = perfil.getFoto();
        // Join list to string for DTO if entity still has list (or handle nulls)
        this.certificados = perfil.getCertificados() != null ? String.join(", ", perfil.getCertificados()) : "";
        this.experiencia = perfil.getExperiencia() != null ? String.join(", ", perfil.getExperiencia()) : "";

        if (perfil.getTutorias() != null) {
            this.tutorias = perfil.getTutorias().stream().map(tutoria -> new TutoriasDTO(tutoria))
                    .collect(Collectors.toList());
        }
        if (perfil.getPersona() != null) {
            this.personaId = perfil.getPersona().getId();
        }

    }

    public PerfilesDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCertificados() {
        return certificados;
    }

    public void setCertificados(String certificados) {
        this.certificados = certificados;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public List<TutoriasDTO> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<TutoriasDTO> tutorias) {
        this.tutorias = tutorias;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
}
