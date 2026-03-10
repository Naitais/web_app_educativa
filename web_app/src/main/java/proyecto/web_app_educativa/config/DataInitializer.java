package proyecto.web_app_educativa.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import proyecto.web_app_educativa.models.Dia;
import proyecto.web_app_educativa.models.DiasDeLaSemana;
import proyecto.web_app_educativa.repositories.DiaRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DiaRepository diaRepository;

    public DataInitializer(DiaRepository diaRepository) {
        this.diaRepository = diaRepository;
    }

    @Override
    public void run(String... args) {
        // Seed the dias table with all days of the week if empty
        if (diaRepository.count() == 0) {
            for (DiasDeLaSemana dia : DiasDeLaSemana.values()) {
                diaRepository.save(new Dia(dia));
            }
        }
    }
}
