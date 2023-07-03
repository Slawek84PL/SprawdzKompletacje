package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import pl.slawek.SprawdzKompletacje.backend.entity.DataService;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ClientHeadersReader;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Header;
import pl.slawek.SprawdzKompletacje.backend.entity.config.MyHeadersList;
import pl.slawek.SprawdzKompletacje.front.MainView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Route(layout = MainView.class)
class ImportConfigView extends VerticalLayout {

    private final DataService dataService;
    private final MyHeadersList myHeadersListSource = new MyHeadersList();

    private final ClientHeadersReader columnReader = new ClientHeadersReader();
    private List<Header> clientHeaders;
    private Button saveButton = new Button();
    private HeaderView headerView = new HeaderView();
    private TextField cofigName = new TextField();

    public ImportConfigView(final DataService dataService) {
        this.dataService = dataService;
    }

    private void createConfigName() {
        cofigName.setLabel("Podaj nazwę konfiguracji");
        cofigName.setMinLength(3);
        cofigName.addValueChangeListener(event -> {
           if (dataService.isExistConfigName(event.getValue())) {
               cofigName.focus();
               saveButton.setVisible(false);
           } else {
               saveButton.setVisible(true);
               saveButton.focus();
           }
        });
    }

    private void createSaveButton() {
        saveButton.setText("Zapisz konfigurację");
        saveButton.setIcon(VaadinIcon.BUTTON.create());
        saveButton.setVisible(false);
        saveButton.addClickListener(event -> {

            if (cofigName.getValue().length() >= 3 && myHeadersListSource.getMyHeadersList().isEmpty()) {
                dataService.saveConfig(headerView.getConfig(), cofigName.getValue());
//                headerView.clearHeaderView();
                this.removeAll();
            } else {
                cofigName.setPlaceholder("Nazwa musi mieć min 3 znaki");
            }
        });
    }

    public void createView() {
        createConfigName();
        createSaveButton();
        add(cofigName);
        add(new HorizontalLayout(
                new Label("Index kolumny klienta"),
                new Label("Nazwa kolumny klienta"),
                new Label("Jako twoja kolumna"))
        );


        for (Header header : clientHeaders) {
            headerView = new HeaderView();
            headerView.setColumnIndex(header.getColumnIndex());
            headerView.setColumnName(header.getColumnName());
            headerView.createCombo();
            add(headerView);
        }

        add(saveButton);
    }

    public void importHeaders(File file) throws IOException {
        clientHeaders = columnReader.readExcelFile(file);
    }
}
