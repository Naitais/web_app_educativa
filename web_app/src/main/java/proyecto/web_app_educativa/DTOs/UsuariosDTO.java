package proyecto.web_app_educativa.DTOs;


import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Usuarios;
import java.time.LocalDateTime;

public class UsuariosDTO {
    private int id;
    private UsuarioEstados estado;
    private LocalDateTime ultimaSesion;
    private String email;
    private String contraseña;
    private String confirmContraseña;
    private LocalDateTime fechaRegistro;
    private Roles rol;

    // Campos agregados para la creacion de la Persona durante el registro
    private String nombre;
    private String apellido;
    private String numCelular;
    private java.time.LocalDate fechaNacimiento;
    private String nombreAdulto;
    private String numCelularAdulto;

    public UsuariosDTO(Usuarios usuario) {
        this.id = usuario.getId();
        this.estado = usuario.getEstadoUsuario();
        this.ultimaSesion = usuario.getUltimaSesion();
        this.email = usuario.getEmail();
        this.contraseña = usuario.getContraseña();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.rol = usuario.getRol();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.numCelular = usuario.getNumCelular();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.nombreAdulto = usuario.getNombreAdulto();
        this.numCelularAdulto = usuario.getNumCelularAdulto();
    }

    public UsuariosDTO() {

    }

    public int getId() {
        return id;
    }

    public UsuarioEstados getEstado() {
        return estado;
    }

    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getConfirmContraseña() {
        return confirmContraseña;
    }

    public void setConfirmContraseña(String confirmContraseña) {
        this.confirmContraseña = confirmContraseña;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public Roles getRol() {
        return rol;
    }

    public void setEstado(UsuarioEstados estado) {
        this.estado = estado;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
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

    public java.time.LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.time.LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreAdulto() {
        return nombreAdulto;
    }

    public void setNombreAdulto(String nombreAdulto) {
        this.nombreAdulto = nombreAdulto;
    }

    public String getNumCelularAdulto() {
        return numCelularAdulto;
    }

    public void setNumCelularAdulto(String numCelularAdulto) {
        this.numCelularAdulto = numCelularAdulto;
    }
}
