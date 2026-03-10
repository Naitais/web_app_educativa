package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Certificado;
import proyecto.web_app_educativa.models.Experiencia;
import proyecto.web_app_educativa.models.Perfiles;
import java.util.List;
import java.util.stream.Collectors;

public class PerfilesDTO {

    private int id;
    private boolean estado;
    private double rating;
    private String biografia;
    private String foto; // es un link
    private List<String> certificados; // por ahora solo son strings
    private List<String> experiencia;
    private List<Experiencia> experienciaCompleta;
    private List<Certificado> certificadosCompletos;
    private List<TutoriasDTO> tutorias;
    private String nombrePersona;
    private String apellidoPersona;
    private List<ComentarioPerfilDTO> comentarios;

    public PerfilesDTO(Perfiles perfil) {
        this.id = perfil.getId();
        this.estado = perfil.getEstado();
        this.rating = perfil.getRating();
        this.biografia = perfil.getBiografia();
        this.foto = perfil.getFoto();
        this.certificados = perfil.getCertificados().stream().map(c -> c.getNombre()).collect(Collectors.toList());
        this.experiencia = perfil.getExperiencia().stream().map(e -> e.getTitulo()).collect(Collectors.toList());
        this.experienciaCompleta = perfil.getExperiencia();
        this.certificadosCompletos = perfil.getCertificados();
        this.tutorias = perfil.getTutorias().stream().map(tutoria -> new TutoriasDTO(tutoria))
                .collect(Collectors.toList());
        if(perfil.getPersona() != null) {
            this.nombrePersona = perfil.getPersona().getNombre();
            this.apellidoPersona = perfil.getPersona().getApellido();
        }
        if (perfil.getComentarios() != null) {
            this.comentarios = perfil.getComentarios().stream().map(c -> new ComentarioPerfilDTO(
                c.getId(),
                c.getAutor().getNombre() + " " + c.getAutor().getApellido(),
                c.getPuntaje(),
                c.getComentario(),
                c.getFechaCreacion()
            )).collect(Collectors.toList());
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

    public List<String> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<String> certificados) {
        this.certificados = certificados;
    }

    public List<String> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(List<String> experiencia) {
        this.experiencia = experiencia;
    }

    public List<TutoriasDTO> getTutorias() {
        return tutorias;
    }

    public void setTutorias(List<TutoriasDTO> tutorias) {
        this.tutorias = tutorias;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }

    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public List<ComentarioPerfilDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioPerfilDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Experiencia> getExperienciaCompleta() {
        return experienciaCompleta;
    }

    public List<Certificado> getCertificadosCompletos() {
        return certificadosCompletos;
    }
}
