package proyecto.web_app_educativa.models;

public enum TiposPagos {
    POR_HORA("Por hora"),
    POR_CLASE("Por clase"),
    POR_SEMANA("Por semana"),
    POR_MES("Por mes");

    private final String displayName;

    TiposPagos(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
