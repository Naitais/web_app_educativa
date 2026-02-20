package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import proyecto.web_app_educativa.DTOs.PersonasDTO;
import proyecto.web_app_educativa.models.OrdsResponse;

import java.net.URI;
import java.util.List;

@Service
public class PersonasService {

    private final String APEX_URL = "https://oracleapex.com/ords/wksp_enzof9849/personas/";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<PersonasDTO> getPersonasActivos() {
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", "{\"estado\":1}")
                .build()
                .toUri();

        OrdsResponse<PersonasDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<PersonasDTO>>() {
                }).getBody();

        return response != null ? response.getItems() : List.of();
    }

    public PersonasDTO findPersonaById(int id) {
        try {
            return restTemplate.getForObject(APEX_URL + id, PersonasDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public PersonasDTO crearPersona(PersonasDTO personaDTO) {
        // Ensure ID is 0 for creation
        personaDTO.setId(0);
        return restTemplate.postForObject(APEX_URL, personaDTO, PersonasDTO.class);
    }

    public void actualizarPersona(int id, PersonasDTO personaDTO) {
        restTemplate.put(APEX_URL + id, personaDTO);
    }
}
