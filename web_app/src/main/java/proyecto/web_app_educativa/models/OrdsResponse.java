package proyecto.web_app_educativa.models;

import java.util.List;

public class OrdsResponse<T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}