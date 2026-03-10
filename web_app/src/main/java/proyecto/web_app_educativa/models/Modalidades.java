package proyecto.web_app_educativa.models;

public enum Modalidades {
    VIRTUAL("Virtual"),
    PRESENCIAL("Presencial"),
    HIBRIDO("Híbrido");

    private final String displayName;

    Modalidades(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
