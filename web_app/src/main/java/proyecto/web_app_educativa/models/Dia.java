package proyecto.web_app_educativa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dias")
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private DiasDeLaSemana nombre;

    public Dia() {}

    public Dia(DiasDeLaSemana nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiasDeLaSemana getNombre() {
        return nombre;
    }

    public void setNombre(DiasDeLaSemana nombre) {
        this.nombre = nombre;
    }
}
