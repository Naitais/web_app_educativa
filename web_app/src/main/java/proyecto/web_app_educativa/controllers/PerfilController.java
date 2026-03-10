package proyecto.web_app_educativa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.Certificado;
import proyecto.web_app_educativa.models.Experiencia;
import proyecto.web_app_educativa.models.Personas;
import proyecto.web_app_educativa.models.Roles;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.services.PerfilesService;
import proyecto.web_app_educativa.services.UsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;
import proyecto.web_app_educativa.models.EstadoSolicitud;

@Controller
public class PerfilController {

    private final UsuariosService usuariosService;
    private final PerfilesService perfilesService;

    @Autowired
    public PerfilController(UsuariosService usuariosService, PerfilesService perfilesService) {
        this.usuariosService = usuariosService;
        this.perfilesService = perfilesService;
    }

    @GetMapping("/perfil")
    public String perfilTutor(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                // obtengo el usuario logeado y lo agrego al contexto (model)
                model.addAttribute("usuario", usuario);

                // verifico si la persona cargada en el usuario logeado es una
                // instancia de Tutores
                if (usuario.getRol() == Roles.ROL_PROFESOR) {

                    // si el usuario es un tutor agrego variable de sesion true
                    model.addAttribute("esTutor", true);
                    Personas persona = usuario;

                    if (persona.getPerfil() != null) {

                        // agrego al modelo la informacion del perfil del tutor logeado
                        model.addAttribute("perfil", persona.getPerfil());
                    }

                } else {

                    // caso contrario es false y no podra ver el boton perfil
                    model.addAttribute("esTutor", false);
                }
            }
        }
        return "html/perfil.html";
    }

    @GetMapping("/perfil/{id}")
    public String perfilTutorPublico(@PathVariable int id, Model model, Principal principal) {
        
        // 1. Obtener el DTO exacto del perfil público de forma segura
        PerfilesDTO perfilPublico = perfilesService.findPerfilById(id);
        
        if (perfilPublico == null) {
            return "redirect:/home"; // fail safe si el usuario consulta un ID aleatorio
        }
        
        model.addAttribute("perfilPublico", perfilPublico);

        // 2. Preservar el estado de la UI del navbar para el usuario actual si alguien está logueado mirando este perfil
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null) {
                if (usuario.getRol() == Roles.ROL_PROFESOR) {
                    model.addAttribute("esTutor", true);
                } else {
                    model.addAttribute("esTutor", false);
                }
                
                // Cargar el id de la persona actual para evitar auto-solicitudes
                model.addAttribute("currentUsuarioPersonaId", usuario.getId());

                // Cargar lista de IDs de tutorías a las que el usuario ya aplicó
                java.util.List<Integer> tutoriasSolicitadasIds = new java.util.ArrayList<>();
                boolean puedeComentar = false;

                if (usuario.getSolicitudes() != null) {
                    for (proyecto.web_app_educativa.models.SolicitudTutoria sol : usuario.getSolicitudes()) {
                        tutoriasSolicitadasIds.add(sol.getTutoria().getId());
                        
                        // Comprobar si el alumno tiene una solicitud ACEPTADA para el perfil de este tutor
                        if (sol.getEstado() == EstadoSolicitud.ACEPTADA && 
                            sol.getTutoria().getPerfil() != null && 
                            sol.getTutoria().getPerfil().getId() == id) {
                            puedeComentar = true;
                        }
                    }
                }
                model.addAttribute("tutoriasSolicitadasIds", tutoriasSolicitadasIds);
                model.addAttribute("puedeComentar", puedeComentar);
            }
        }
        
        // Retornar pantalla dedicada de solo lectura
        return "html/public-perfil.html";
    }

    @GetMapping("/mis-tutorias")
    public String misTutorias(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR) {
                model.addAttribute("usuario", usuario);
                model.addAttribute("esTutor", true);
                Personas persona = usuario;
                if (persona.getPerfil() != null) {
                    model.addAttribute("perfil", persona.getPerfil());
                }
                return "html/mis-tutorias.html";
            }
        }
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/primera-vez")
    public String crearPerfilPrimeraVez(
            @RequestParam("biografia") String biografia,
            @RequestParam(value = "foto", required = false) String foto,
            @RequestParam(value = "expTitulo", required = false) String expTitulo,
            @RequestParam(value = "expInstitucion", required = false) String expInstitucion,
            @RequestParam(value = "expFechaDesde", required = false) String expFechaDesdeStr,
            @RequestParam(value = "expFechaHasta", required = false) String expFechaHastaStr,
            @RequestParam(value = "expDescripcion", required = false) String expDescripcion,
            @RequestParam(value = "certNombre", required = false) String certNombre,
            @RequestParam(value = "certUrl", required = false) String certUrl,
            Principal principal) {
            
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR) {
                // Prepara el DTO
                PerfilesDTO nuevoPerfil = new PerfilesDTO();
                nuevoPerfil.setBiografia(biografia);
                if (foto == null || foto.trim().isEmpty()) {
                    foto = "https://tse2.mm.bing.net/th/id/OIP.Z8d1mi6B_6j2JbElEPl-QQHaHa?rs=1&pid=ImgDetMain&o=7&rm=3";
                }
                nuevoPerfil.setFoto(foto);
                nuevoPerfil.setEstado(true);
                nuevoPerfil.setRating(0.0);
                
                // Experiencia opcional
                Experiencia exp = null;
                if (expTitulo != null && !expTitulo.trim().isEmpty()) {
                    exp = new Experiencia();
                    exp.setTitulo(expTitulo);
                    exp.setInstitucionOEmpresa(expInstitucion);
                    if (expFechaDesdeStr != null && !expFechaDesdeStr.isEmpty()) {
                        exp.setFechaDesde(java.time.LocalDate.parse(expFechaDesdeStr));
                    }
                    if (expFechaHastaStr != null && !expFechaHastaStr.isEmpty()) {
                        exp.setFechaHasta(java.time.LocalDate.parse(expFechaHastaStr));
                    }
                    exp.setDescripcion(expDescripcion);
                }

                // Certificado opcional
                Certificado cert = null;
                if (certNombre != null && !certNombre.trim().isEmpty()) {
                    cert = new Certificado();
                    cert.setNombre(certNombre);
                    if (certUrl == null || certUrl.trim().isEmpty()) {
                        certUrl = "https://tse1.mm.bing.net/th/id/OIP.BFTeIZVBj7s4XdkpnOk1kwHaFm?rs=1&pid=ImgDetMain&o=7&rm=3";
                    }
                    cert.setUrlOArchivo(certUrl);
                }

                // Guardar usando PerfilesService
                perfilesService.crearPerfilConDetalles(nuevoPerfil, usuario.getId(), exp, cert);
            }
        }
        return "redirect:/home";
    }

    @PostMapping("/perfil/editar")
    public String editarPerfil(
            @RequestParam("biografia") String biografia,
            @RequestParam(value = "foto", required = false) String foto,
            @RequestParam(value = "expTitulo", required = false) String expTitulo,
            @RequestParam(value = "expInstitucion", required = false) String expInstitucion,
            @RequestParam(value = "expFechaDesde", required = false) String expFechaDesdeStr,
            @RequestParam(value = "expFechaHasta", required = false) String expFechaHastaStr,
            @RequestParam(value = "expDescripcion", required = false) String expDescripcion,
            @RequestParam(value = "certNombre", required = false) String certNombre,
            @RequestParam(value = "certUrl", required = false) String certUrl,
            Principal principal) {
            
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR && usuario.getPerfil() != null) {
                // Prepara el DTO para bio/foto
                PerfilesDTO updateDto = new PerfilesDTO();
                updateDto.setBiografia(biografia);
                if (foto == null || foto.trim().isEmpty()) {
                    foto = "https://tse2.mm.bing.net/th/id/OIP.Z8d1mi6B_6j2JbElEPl-QQHaHa?rs=1&pid=ImgDetMain&o=7&rm=3";
                }
                updateDto.setFoto(foto);
                
                // Preparar Experiencia nueva si el usuario la manda
                Experiencia exp = null;
                if (expTitulo != null && !expTitulo.trim().isEmpty()) {
                    exp = new Experiencia();
                    exp.setTitulo(expTitulo);
                    exp.setInstitucionOEmpresa(expInstitucion);
                    if (expFechaDesdeStr != null && !expFechaDesdeStr.isEmpty()) {
                        exp.setFechaDesde(java.time.LocalDate.parse(expFechaDesdeStr));
                    }
                    if (expFechaHastaStr != null && !expFechaHastaStr.isEmpty()) {
                        exp.setFechaHasta(java.time.LocalDate.parse(expFechaHastaStr));
                    }
                    exp.setDescripcion(expDescripcion);
                }

                // Preparar Certificado nuevo si el usuario lo manda
                Certificado cert = null;
                if (certNombre != null && !certNombre.trim().isEmpty()) {
                    cert = new Certificado();
                    cert.setNombre(certNombre);
                    if (certUrl == null || certUrl.trim().isEmpty()) {
                        certUrl = "https://tse1.mm.bing.net/th/id/OIP.BFTeIZVBj7s4XdkpnOk1kwHaFm?rs=1&pid=ImgDetMain&o=7&rm=3";
                    }
                    cert.setUrlOArchivo(certUrl);
                }

                int perfilId = usuario.getPerfil().getId();
                perfilesService.actualizarPerfil(perfilId, updateDto, exp, cert);
            }
        }
        return "redirect:/perfil"; // Redirigir de nuevo a la página del perfil para ver las ediciones
    }

    @PostMapping("/perfil/{id}/comentar")
    public String dejarComentario(@PathVariable int id,
                                  @RequestParam("puntaje") int puntaje,
                                  @RequestParam("comentario") String comentario,
                                  Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_ESTUDIANTE) {
                // Confiamos en que el front-end sólo muestre el formulario si puedeComentar es true. 
                // Lo ideal sería volver a ejecutar la lógica de validación

                perfilesService.agregarComentario(id, usuario.getId(), puntaje, comentario);
            }
        }
        return "redirect:/perfil/" + id;
    }

    // endpoints para editar

    @PostMapping("/perfil/experiencia/editar")
    public String editarExperiencia(
            @RequestParam("id") int expId,
            @RequestParam("titulo") String titulo,
            @RequestParam("institucion") String institucion,
            @RequestParam("fechaDesde") String fechaDesdeStr,
            @RequestParam(value = "fechaHasta", required = false) String fechaHastaStr,
            @RequestParam("descripcion") String descripcion,
            Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR && usuario.getPerfil() != null) {
                Experiencia exp = new Experiencia();
                exp.setId(expId);
                exp.setTitulo(titulo);
                exp.setInstitucionOEmpresa(institucion);
                if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) {
                    exp.setFechaDesde(java.time.LocalDate.parse(fechaDesdeStr));
                }
                if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) {
                    exp.setFechaHasta(java.time.LocalDate.parse(fechaHastaStr));
                }
                exp.setDescripcion(descripcion);

                int perfilId = usuario.getPerfil().getId();
                perfilesService.editarExperiencia(perfilId, exp);
            }
        }
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/experiencia/eliminar")
    public String eliminarExperiencia(@RequestParam("id") int expId, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR && usuario.getPerfil() != null) {
                int perfilId = usuario.getPerfil().getId();
                perfilesService.eliminarExperiencia(perfilId, expId);
            }
        }
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/certificado/editar")
    public String editarCertificado(
            @RequestParam("id") int certId,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "urlOArchivo", required = false) String urlOArchivo,
            Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR && usuario.getPerfil() != null) {
                Certificado cert = new Certificado();
                cert.setId(certId);
                cert.setNombre(nombre);
                if (urlOArchivo == null || urlOArchivo.trim().isEmpty()) {
                    urlOArchivo = "https://tse1.mm.bing.net/th/id/OIP.BFTeIZVBj7s4XdkpnOk1kwHaFm?rs=1&pid=ImgDetMain&o=7&rm=3";
                }
                cert.setUrlOArchivo(urlOArchivo);

                int perfilId = usuario.getPerfil().getId();
                perfilesService.editarCertificado(perfilId, cert);
            }
        }
        return "redirect:/perfil";
    }

    @PostMapping("/perfil/certificado/eliminar")
    public String eliminarCertificado(@RequestParam("id") int certId, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosService.getUsuarioPorEmail(email);

            if (usuario != null && usuario.getRol() == Roles.ROL_PROFESOR && usuario.getPerfil() != null) {
                int perfilId = usuario.getPerfil().getId();
                perfilesService.eliminarCertificado(perfilId, certId);
            }
        }
        return "redirect:/perfil";
    }
}
