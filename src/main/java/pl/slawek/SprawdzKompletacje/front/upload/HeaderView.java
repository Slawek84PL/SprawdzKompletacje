package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.AllArgsConstructor;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Config;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Header;
import pl.slawek.SprawdzKompletacje.backend.entity.config.MyHeadersList;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class HeaderView extends HorizontalLayout {

    private Label columnIndex = new Label();
    private Label columnName = new Label();

    private final MyHeadersList myHeadersListSource = new MyHeadersList();

    private static final List<HeaderView> viewHeaders = new ArrayList<>();

    private ComboBox<Header> headerNameCombo = new ComboBox<>();
    private static Config config = new Config();

    public HeaderView() {
        columnIndex.setMinWidth("150px");
        columnName.setMinWidth("150px");
        headerNameCombo.setMinWidth("150px");

        add(columnIndex, columnName, headerNameCombo);
        viewHeaders.add(this);
    }

    public void createCombo() {
        headerNameCombo.setItems(myHeadersListSource.getMyHeadersList());
        headerNameCombo.setItemLabelGenerator(Header::getColumnName);
        headerNameCombo.addValueChangeListener(event -> {
            int headerIndex = event.getValue().getColumnIndex();
            addHeaderToConfig(headerIndex);
            myHeadersListSource.removeFromList(event.getValue());
            headerNameCombo.setReadOnly(true);

            if (myHeadersListSource.getMyHeadersList().isEmpty()) {
                changeReadOnlyForTrue();
            } else {
                updateCombosItems();
            }
        });
    }

    private void addHeaderToConfig(final int headerIndex) {
        switch (headerIndex) {
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

    public void clearHeaderView() {
        config = new Config();
    }

    public Config getConfig() {
        return config;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex.setText(String.valueOf(columnIndex));
    }

    public void setColumnName(String columnName) {
        this.columnName.setText(columnName);
    }

    private void changeReadOnlyForTrue() {
        for (HeaderView combo: viewHeaders){
            if (!combo.headerNameCombo.isReadOnly()) {
                combo.headerNameCombo.setReadOnly(true);
            }
        }
    }

    private void updateCombosItems() {
        for (HeaderView combo: viewHeaders){
            if (!combo.headerNameCombo.isReadOnly()) {
                combo.headerNameCombo.setItems(myHeadersListSource.getMyHeadersList());
            }
        }
    }
}
