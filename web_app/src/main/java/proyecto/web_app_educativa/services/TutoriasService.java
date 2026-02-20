package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import proyecto.web_app_educativa.DTOs.TutoriasDTO;
import proyecto.web_app_educativa.models.OrdsResponse;

import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@Service
public class TutoriasService {

    // Your APEX URL (make sure it ends with /)
    private final String APEX_URL = "https://oracleapex.com/ords/wksp_enzof9849/tutorias/";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<TutoriasDTO> getTutoriasActivas() {
        // ORDS Filter: q={"estado":1} mimics findByEstadoTrue
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", "{\"estado\":1}")
                .build()
                .toUri();

        OrdsResponse<TutoriasDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<TutoriasDTO>>() {
                }).getBody();

        return response.getItems();
    }

    public TutoriasDTO findTutoriaById(int id) {
        // GET .../tutorias/5
        return restTemplate.getForObject(APEX_URL + id, TutoriasDTO.class);
    }

    public List<TutoriasDTO> buscarTutoriasPorPalabra(String palabra) {
        // ORDS Filter: Like search on 'disciplina' (adjust column as needed)
        String jsonQuery = "{\"disciplina\":{\"$like\":\"%" + palabra + "%\"}}";
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", jsonQuery)
                .build()
                .toUri();

        OrdsResponse<TutoriasDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<TutoriasDTO>>() {
                }).getBody();

        return response.getItems();
    }

    public void crearTutoria(TutoriasDTO tutoriaDTO, int perfilId) {
        // Map DTO to the ID of the profile for the database FK
        // In the ORDS POST, we just send the object with the FK included
        tutoriaDTO.setPerfilId(perfilId);
        restTemplate.postForObject(APEX_URL, tutoriaDTO, Void.class);
    }

    public void actualizarTutoria(int id, TutoriasDTO tutoriaDTO) {
        // PUT .../tutorias/5
        restTemplate.put(APEX_URL + id, tutoriaDTO);
    }

    public void borrarTutoria(int id) {
        // Logic: Logic delete (set estado = 0)
        TutoriasDTO tutoria = findTutoriaById(id);
        if (tutoria != null) {
            tutoria.setEstado(false);
            restTemplate.put(APEX_URL + id, tutoria);
        }
    }

}
