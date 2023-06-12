package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;

class HeaderView extends HorizontalLayout {
    private Label columnIndex = new Label();
    private Label columnName = new Label();
    private static List<String> entityHeaders = new ArrayList<>();
    private ComboBox<String> headerName = new ComboBox<>();


    HeaderView(int index, String name) {
        createEntityHeaderList();
        createCombo();
        columnIndex.setText(String.valueOf(index));
        columnName.setText(name);
        columnIndex.setMinWidth("150px");
        columnName.setMinWidth("150px");
        headerName.setMinWidth("150px");

        add(columnIndex, columnName, headerName);
    }

    private void createCombo() {
        headerName.setItems(entityHeaders);
        headerName.addValueChangeListener(event -> {

            entityHeaders.remove(event.getValue());
            headerName.setReadOnly(true);

        });
    }

    private static void createEntityHeaderList() {
        if (entityHeaders.isEmpty()) {
            entityHeaders.add("Kod kreskowy");
            entityHeaders.add("Nazwa produktu");
            entityHeaders.add("Ilość zamówiona");
        }
    }

}
