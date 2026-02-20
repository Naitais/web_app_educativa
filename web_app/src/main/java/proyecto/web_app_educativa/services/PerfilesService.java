package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import proyecto.web_app_educativa.DTOs.PerfilesDTO;
import proyecto.web_app_educativa.models.OrdsResponse;

import java.net.URI;
import java.util.List;

@Service
public class PerfilesService {

    private final String APEX_URL = "https://oracleapex.com/ords/wksp_enzof9849/perfiles/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final TutoriasService tutoriasService;

    public PerfilesService(TutoriasService tutoriasService) {
        this.tutoriasService = tutoriasService;
    }

    public List<PerfilesDTO> getPerfilesActivos() {
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", "{\"estado\":1}")
                .build()
                .toUri();

        OrdsResponse<PerfilesDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<PerfilesDTO>>() {
                }).getBody();

        return response != null ? response.getItems() : List.of();
    }

    public PerfilesDTO findPerfilById(int id) {
        try {
            PerfilesDTO perfil = restTemplate.getForObject(APEX_URL + id, PerfilesDTO.class);
            if (perfil != null) {
                perfil.setTutorias(tutoriasService.getTutoriasByPerfilId(perfil.getId()));
            }
            return perfil;
        } catch (Exception e) {
            return null;
        }
    }

    public PerfilesDTO findPerfilByPersonaId(int personaId) {
        String jsonQuery = "{\"persona_id\":" + personaId + "}";
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", jsonQuery)
                .build()
                .toUri();

        OrdsResponse<PerfilesDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<PerfilesDTO>>() {
                }).getBody();

        if (response != null && !response.getItems().isEmpty()) {
            PerfilesDTO perfil = response.getItems().get(0);
            perfil.setTutorias(tutoriasService.getTutoriasByPerfilId(perfil.getId()));
            return perfil;
        }
        return null;
    }

    public PerfilesDTO crearPerfil(PerfilesDTO perfilDTO, int personaId) {
        perfilDTO.setPersonaId(personaId);
        perfilDTO.setId(0);
        return restTemplate.postForObject(APEX_URL, perfilDTO, PerfilesDTO.class);
    }

    public void actualizarPerfil(int id, PerfilesDTO perfilDTO) {
        restTemplate.put(APEX_URL + id, perfilDTO);
    }
}
