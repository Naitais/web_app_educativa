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

    private final String APEX_URL = "https://oracleapex.com/ords/wksp_enzof9849/tutorias/";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<TutoriasDTO> getTutoriasActivas() {
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
        return restTemplate.getForObject(APEX_URL + id, TutoriasDTO.class);
    }

    public List<TutoriasDTO> buscarTutoriasPorPalabra(String palabra) {
        String lowerPalabra = palabra.toLowerCase();
        return getTutoriasActivas().stream()
                .filter(t -> t.getDisciplina() != null && t.getDisciplina().toLowerCase().contains(lowerPalabra))
                .toList();
    }

    public List<TutoriasDTO> getTutoriasByPerfilId(int perfilId) {
        String jsonQuery = "{\"perfil_id\":" + perfilId + ", \"estado\":1}";
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", jsonQuery)
                .build()
                .toUri();

        OrdsResponse<TutoriasDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<TutoriasDTO>>() {
                }).getBody();

        return response != null ? response.getItems() : List.of();
    }

    public void crearTutoria(TutoriasDTO tutoriaDTO, int perfilId) {
        tutoriaDTO.setPerfilId(perfilId);
        restTemplate.postForObject(APEX_URL, tutoriaDTO, Void.class);
    }

    public void actualizarTutoria(int id, TutoriasDTO tutoriaDTO) {
        restTemplate.put(APEX_URL + id, tutoriaDTO);
    }

    public void borrarTutoria(int id) {
        TutoriasDTO tutoria = findTutoriaById(id);
        if (tutoria != null) {
            tutoria.setEstado(false);
            restTemplate.put(APEX_URL + id, tutoria);
        }
    }

}
