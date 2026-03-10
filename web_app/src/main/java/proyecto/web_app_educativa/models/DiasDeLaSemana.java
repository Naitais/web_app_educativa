package proyecto.web_app_educativa.models;

public enum DiasDeLaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String displayName;

    DiasDeLaSemana(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DiasDeLaSemana fromDisplayName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (DiasDeLaSemana dia : values()) {
            if (dia.displayName.equalsIgnoreCase(name) || dia.name().equalsIgnoreCase(name)) {
                return dia;
            }
        }
        // en caso de acentos
        if ("Miercoles".equalsIgnoreCase(name)) return MIERCOLES;
        if ("Sabado".equalsIgnoreCase(name)) return SABADO;
        
        throw new IllegalArgumentException("Día no válido: " + name);
    }
}
