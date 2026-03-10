package proyecto.web_app_educativa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
    
    private String urlOArchivo; // Para guardar el link de la foto o el archivo si hace falta

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonIgnore
    private Perfiles perfil;

    public Certificado() {
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

    public String getUrlOArchivo() {
        return urlOArchivo;
    }

    public void setUrlOArchivo(String urlOArchivo) {
        this.urlOArchivo = urlOArchivo;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
