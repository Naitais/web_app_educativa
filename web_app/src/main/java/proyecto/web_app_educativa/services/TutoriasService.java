package proyecto.web_app_educativa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.Perfiles;
import proyecto.web_app_educativa.models.Tutores;
import proyecto.web_app_educativa.models.Tutorias;
import proyecto.web_app_educativa.repositories.PerfilesRepository;
import proyecto.web_app_educativa.repositories.TutoriasRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutoriasService {

    private final TutoriasRepository tutoriasRepository;
    private final PerfilesRepository perfilesRepository;

    @Autowired

    public TutoriasService(TutoriasRepository tutoriasRepository, PerfilesRepository perfilesRepository) {
        this.tutoriasRepository = tutoriasRepository;
        this.perfilesRepository = perfilesRepository;
    }


    public List<TutoriasDTO> getTutoriasActivas(){
        return tutoriasRepository.findByEstadoTrue().stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    public TutoriasDTO findTutoriaById(int id){
        Tutorias tutorias =  tutoriasRepository.findById(id).orElse(null);
        return new TutoriasDTO(tutorias);
    }

    //filtro con palabra clave solo funciona con una sola palabra
    //todo en un futuro modificar metodo para que pueda incluir busquedas booleanas
    public List<TutoriasDTO> buscarTutoriasPorPalabra(String palabra) {
        List<Tutorias> tutorias = tutoriasRepository.buscarPorPalabra(palabra);
        return tutorias.stream()
                .map(TutoriasDTO::new)
                .collect(Collectors.toList());
    }

    /*// para filtrar con el nombre del tutor
    public List<Tutorias> obtenerTutoriasPorTutor(Tutores tutor) {

        return tutoriasRepository.findByTutorAndEstadoTrue(tutor);
    }
*/
    public Tutorias crearTutoria(TutoriasDTO tutoriaDTO, int id) {
        //busco por id perfil al cual le agrego la tutoria
        Perfiles perfil =  perfilesRepository.findById(id).orElse(null);

        Tutorias tutoria = new Tutorias(

                        tutoriaDTO.getEdadMinima(),
                        tutoriaDTO.getHorarioDesde(),
                        tutoriaDTO.getHorarioHasta(),
                        tutoriaDTO.getFechaDesde(),
                        tutoriaDTO.getFechaHasta(),
                        tutoriaDTO.getDias(),
                        tutoriaDTO.getTipoUbicaciones(),
                        tutoriaDTO.getDisciplina(),
                        tutoriaDTO.getMateriales(),
                        tutoriaDTO.getUbicacion(),
                        tutoriaDTO.getEstado(),
                        tutoriaDTO.getDescripcion(),
                        tutoriaDTO.getTipoPago(),
                        tutoriaDTO.getModalidad(),
                        tutoriaDTO.getArancel()

                );
        //agrego la tutoria
        perfil.agregarTutoria(tutoria);

        return tutoriasRepository.save(tutoria);
    }

    public Tutorias actualizarTutoria(int id,TutoriasDTO tutoriaDTO){
        Tutorias tutoria = new Tutorias(
                tutoriaDTO.getEdadMinima(), //
                tutoriaDTO.getHorarioDesde(),
                tutoriaDTO.getHorarioHasta(),
                tutoriaDTO.getFechaDesde(),
                tutoriaDTO.getFechaHasta(),
                tutoriaDTO.getDias(),
                tutoriaDTO.getTipoUbicaciones(), //
                tutoriaDTO.getDisciplina(), //
                tutoriaDTO.getMateriales(), //
                tutoriaDTO.getUbicacion(), //
                tutoriaDTO.getEstado(),
                tutoriaDTO.getDescripcion(),
                tutoriaDTO.getTipoPago(), //
                tutoriaDTO.getModalidad(), //
                tutoriaDTO.getArancel() //
        );
        tutoria.setId(id);
        return tutoriasRepository.save(tutoria);
    }
    //TODO agregar metodo delete pero que haga update
    public Tutorias borrarTutoria(int id) {
        Tutorias tutoria = tutoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoria no encontrada"));

        tutoria.setEstado(false);

        return tutoriasRepository.save(tutoria);
    }



}
