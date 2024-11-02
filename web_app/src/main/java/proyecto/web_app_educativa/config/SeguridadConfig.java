package proyecto.web_app_educativa.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import proyecto.web_app_educativa.services.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SeguridadConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SeguridadConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/public/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}) // Replaces httpBasic() with the latest API
                .formLogin(form -> form
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disables CSRF for testing purposes

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
