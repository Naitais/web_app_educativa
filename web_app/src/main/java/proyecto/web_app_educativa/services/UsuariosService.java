package proyecto.web_app_educativa.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import proyecto.web_app_educativa.DTOs.UsuariosDTO;
import proyecto.web_app_educativa.models.OrdsResponse;
import proyecto.web_app_educativa.models.Usuarios;
import proyecto.web_app_educativa.models.UsuarioEstados;
import proyecto.web_app_educativa.models.Roles;

import java.net.URI;
import java.util.List;

@Service
public class UsuariosService {

    private final String APEX_URL = "https://oracleapex.com/ords/wksp_enzof9849/usuarios/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuariosDTO> getUsuariosActivos() {
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", "{\"estado\":\"ACTIVO\"}")
                .build()
                .toUri();

        OrdsResponse<UsuariosDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<UsuariosDTO>>() {
                }).getBody();

        return response != null ? response.getItems() : List.of();
    }

    public UsuariosDTO getUsuarioPorId(int id) {
        try {
            return restTemplate.getForObject(APEX_URL + id, UsuariosDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public UsuariosDTO crearUsuario(UsuariosDTO usuariosDTO) {
        String contraseñaCodificada = passwordEncoder.encode(usuariosDTO.getContraseña());
        usuariosDTO.setContraseña(contraseñaCodificada);
        usuariosDTO.setId(0);
        return restTemplate.postForObject(APEX_URL, usuariosDTO, UsuariosDTO.class);
    }

    public void actualizarUsuario(int id, UsuariosDTO usuariosDTO) {
        restTemplate.put(APEX_URL + id, usuariosDTO);
    }

    public Usuarios getUsuarioPorEmail(String email) {
        String jsonQuery = "{\"email\":\"" + email + "\"}";
        URI uri = UriComponentsBuilder.fromHttpUrl(APEX_URL)
                .queryParam("q", jsonQuery)
                .build()
                .toUri();

        OrdsResponse<UsuariosDTO> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrdsResponse<UsuariosDTO>>() {
                }).getBody();

        if (response != null && !response.getItems().isEmpty()) {
            UsuariosDTO dto = response.getItems().get(0);
            Usuarios usuario = new Usuarios();
            usuario.setId(dto.getId());
            usuario.setEmail(dto.getEmail());
            usuario.setContraseña(dto.getContraseña());
            usuario.setRol(dto.getRol());
            usuario.setEstado(dto.getEstado());
            usuario.setPersonaId(dto.getPersonaId());
            return usuario;
        }
        return null;
    }
}
