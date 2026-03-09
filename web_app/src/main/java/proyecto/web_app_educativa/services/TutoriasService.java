package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;
import proyecto.web_app_educativa.repositories.CategoriaRepository;
import proyecto.web_app_educativa.models.Categoria;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutoriasService {

    private final TutoriasRepository tutoriasRepository;
    private final PerfilesRepository perfilesRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired

    public TutoriasService(TutoriasRepository tutoriasRepository, PerfilesRepository perfilesRepository, CategoriaRepository categoriaRepository) {
        this.tutoriasRepository = tutoriasRepository;
        this.perfilesRepository = perfilesRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<TutoriasDTO> getTutoriasActivas() {
        return tutoriasRepository.findByEstadoTrue().stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    public TutoriasDTO findTutoriaById(int id) {
        Tutorias tutorias = tutoriasRepository.findById(id).orElse(null);
        return new TutoriasDTO(tutorias);
    }

    public List<TutoriasDTO> buscarTutoriasAvanzado(
            String palabra, 
            proyecto.web_app_educativa.models.Modalidades modalidad, 
            Double precioMax,
            Integer edadMinima,
            java.time.LocalTime horarioDesde,
            java.time.LocalTime horarioHasta,
            proyecto.web_app_educativa.models.TiposPagos tipoPago,
            proyecto.web_app_educativa.models.TiposUbicaciones tipoUbicacion,
            Integer categoriaId) {
        List<Tutorias> tutorias = tutoriasRepository.buscarAvanzado(palabra, modalidad, precioMax, edadMinima, horarioDesde, horarioHasta, tipoPago, tipoUbicacion, categoriaId);
        return tutorias.stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    /*
     * // para filtrar con el nombre del tutor
     * public List<Tutorias> obtenerTutoriasPorTutor(Tutores tutor) {
     * 
     * return tutoriasRepository.findByTutorAndEstadoTrue(tutor);
     * }
     */
    public Tutorias crearTutoria(TutoriasDTO tutoriaDTO, int id) {
        // busco por id perfil al cual le agrego la tutoria
        Perfiles perfil = perfilesRepository.findById(id).orElse(null);

        Categoria categoria = null;
        if(tutoriaDTO.getDisciplina() != null) {
            categoria = categoriaRepository.findByNombre(tutoriaDTO.getDisciplina());
            if(categoria == null) {
                categoria = new Categoria(tutoriaDTO.getDisciplina());
                categoriaRepository.save(categoria);
            }
        }

        Tutorias tutoria = new Tutorias(

                tutoriaDTO.getEdadMinima(),
                tutoriaDTO.getHorarioDesde(),
                tutoriaDTO.getHorarioHasta(),
                tutoriaDTO.getFechaDesde(),
                tutoriaDTO.getFechaHasta(),
                tutoriaDTO.getDias(),
                tutoriaDTO.getTipoUbicaciones(),
                categoria,
                tutoriaDTO.getMateriales(),
                tutoriaDTO.getUbicacion(),
                tutoriaDTO.getEstado(),
                tutoriaDTO.getDescripcion(),
                tutoriaDTO.getTipoPago(),
                tutoriaDTO.getModalidad(),
                tutoriaDTO.getArancel()

        );
        // agrego la tutoria
        perfil.agregarTutoria(tutoria);

        return tutoriasRepository.save(tutoria);
    }

    public Tutorias actualizarTutoria(int id, TutoriasDTO tutoriaDTO) {
        Tutorias tutoriaExistente = tutoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoria no encontrada"));

        Categoria categoria = null;
        if(tutoriaDTO.getDisciplina() != null) {
            categoria = categoriaRepository.findByNombre(tutoriaDTO.getDisciplina());
            if(categoria == null) {
                categoria = new Categoria(tutoriaDTO.getDisciplina());
                categoriaRepository.save(categoria);
            }
        }
        
        tutoriaExistente.setEdadMinima(tutoriaDTO.getEdadMinima());
        tutoriaExistente.setHorarioDesde(tutoriaDTO.getHorarioDesde());
        tutoriaExistente.setHorarioHasta(tutoriaDTO.getHorarioHasta());
        tutoriaExistente.setFechaDesde(tutoriaDTO.getFechaDesde());
        tutoriaExistente.setFechaHasta(tutoriaDTO.getFechaHasta());
        tutoriaExistente.setDias(tutoriaDTO.getDias());
        tutoriaExistente.setTipoUbicaciones(tutoriaDTO.getTipoUbicaciones());
        tutoriaExistente.setCategoria(categoria);
        tutoriaExistente.setMateriales(tutoriaDTO.getMateriales());
        tutoriaExistente.setUbicacion(tutoriaDTO.getUbicacion());
        tutoriaExistente.setDescripcion(tutoriaDTO.getDescripcion());
        tutoriaExistente.setTipoPago(tutoriaDTO.getTipoPago());
        tutoriaExistente.setModalidad(tutoriaDTO.getModalidad());
        tutoriaExistente.setValorPorClase(tutoriaDTO.getArancel());

        return tutoriasRepository.save(tutoriaExistente);
    }

    // TODO agregar metodo delete pero que haga update
    public Tutorias borrarTutoria(int id) {
        Tutorias tutoria = tutoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoria no encontrada"));

        tutoria.setEstado(false);

        return tutoriasRepository.save(tutoria);
    }

    public Tutorias toggleEstado(int id) {
        Tutorias tutoria = tutoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoria no encontrada"));
        
        // Reverse whatever the current estado boolean is
        tutoria.setEstado(!tutoria.getEstado());

        return tutoriasRepository.save(tutoria);
    }

}
