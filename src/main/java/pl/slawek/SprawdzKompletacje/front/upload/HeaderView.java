package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.AllArgsConstructor;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Config;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class HeaderView extends HorizontalLayout {

    private Label columnIndex = new Label();
    private Label columnName = new Label();
    private static List<String> entityHeaders = new ArrayList<>();
    private ComboBox<String> headerNameCombo = new ComboBox<>();
    private static Config config = new Config();

    public HeaderView(int index, String name) {
        createEntityHeaderList();
        createCombo();
        columnIndex.setText(String.valueOf(index));
        columnName.setText(name);
        columnIndex.setMinWidth("150px");
        columnName.setMinWidth("150px");
        headerNameCombo.setMinWidth("150px");

        add(columnIndex, columnName, headerNameCombo);
    }

    private void createCombo() {
        headerNameCombo.setItems(entityHeaders);
        headerNameCombo.addValueChangeListener(event -> {
            int headerIndex = entityHeaders.indexOf(event.getValue());

            addHeaderToConfig(headerIndex);
            entityHeaders.remove(headerIndex);
            headerNameCombo.setReadOnly(true);

        });
    }

    private void addHeaderToConfig(final int headerView) {
        switch (headerView) {
            case 0 -> {
                config.setBarcodeColumn(this.columnIndex.getText());
            }
            case 1 -> {
                config.setNameColumn(this.columnIndex.getText());
            }
            case 2 -> {
                config.setQuantityColumn(this.columnIndex.getText());
            }
        }
        System.out.println(config);
    }

    private static void createEntityHeaderList() {
        if (entityHeaders.isEmpty()) {
            entityHeaders.add("Kod kreskowy");
            entityHeaders.add("Nazwa produktu");
            entityHeaders.add("Ilość zamówiona");
        }
    }

    public void clearHeaderView() {
        entityHeaders.clear();
        config = new Config();
    }

    public Config getConfig() {
        return config;
    }
}
