package proyecto.web_app_educativa.DTOs;

import proyecto.web_app_educativa.models.Personas;
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
    private Personas personas;

    // Campos agregados para la creacion de la Persona durante el registro
    private String nombre;
    private String apellido;
    private String numCelular;
    private java.time.LocalDate fechaNacimiento;

    public UsuariosDTO(Usuarios usuario) {
        this.id = usuario.getId();
        this.estado = usuario.getEstado();
        this.ultimaSesion = usuario.getUltimaSesion();
        this.email = usuario.getEmail();
        this.contraseña = usuario.getContraseña();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.rol = usuario.getRol();
        this.personas = usuario.getPersona();
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

    public Personas getPersonas() {
        return personas;
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
}
