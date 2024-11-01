package proyecto.web_app_educativa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import proyecto.web_app_educativa.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SeguridadConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // Configure the SecurityFilterChain for HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/register", "/public/**").permitAll() //endpoints publicos
                        .requestMatchers("/api/**").authenticated()
                        //.anyRequest().authenticated() // debe estar logeado para acceder al resto de end points
                )

                .formLogin((form) -> form
                        //.loginPage("/login") // redirecciona al login
                        .defaultSuccessUrl("/api/usuarios", true) // redirecciona despues de login
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    // repasar que hace codigo de abajo
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // Optional: Customize AuthenticationManagerBuilder if you want to configure additional providers
    /*@Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        return auth;
    }*/
}

