package proyecto.web_app_educativa.models;

public enum TiposUbicaciones {
    DOMICILIO_TUTOR("Domicilio del tutor"),
    DOMICILIO_ESTUDIANTE("Domicilio del estudiante"),
    OTRO("Otro");

    private final String displayName;

    TiposUbicaciones(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
