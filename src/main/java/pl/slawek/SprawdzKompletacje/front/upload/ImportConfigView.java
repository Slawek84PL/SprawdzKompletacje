package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import pl.slawek.SprawdzKompletacje.backend.entity.DataService;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigColumnReader;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigHeader;
import pl.slawek.SprawdzKompletacje.front.MainView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Route(layout = MainView.class)
class ImportConfigView extends VerticalLayout {

    private final DataService dataService;

    private final ConfigColumnReader columnReader = new ConfigColumnReader();
    private List<ConfigHeader> clientHeaders;
    private List<HeaderView> viewHeaders = new ArrayList<>();
    private Button saveButton = new Button();
    private HeaderView headerView;
    private TextField cofigName = new TextField();

    public ImportConfigView(final DataService dataService) {
        this.dataService = dataService;
    }

    private void createConfigName() {
        cofigName.setLabel("Podaj nazwę konfiguracji");
        cofigName.setMinLength(3);
    }

    private void createSaveButton() {
        saveButton.setText("Zapisz konfigurację");
        saveButton.setIcon(VaadinIcon.BUTTON.create());
        saveButton.addClickListener(event -> {

            if (cofigName.getValue().length() >= 3) {
                dataService.saveConfig(headerView.getConfig(), cofigName.getValue());
                headerView.clearHeaderView();
            } else {
                cofigName.setErrorMessage("Nazwa musi mieć min 3 znaki");
            }
        });
    }

    public void createView() {
        createConfigName();
        createSaveButton();
        add(new HorizontalLayout(
                new Label("Index kolumny klienta"),
                new Label("Nazwa kolumny klienta"),
                new Label("Jako twoja kolumna"))
        );

        add(cofigName);

        for (ConfigHeader header : clientHeaders) {
            headerView = new HeaderView(header.getColumnIndex(), header.getColumnName());
            viewHeaders.add(headerView);
            add(headerView);
        }

        add(saveButton);
    }

    public void importHeaders(File file) throws IOException {
        clientHeaders = columnReader.readExcelFile(file);
    }
}
