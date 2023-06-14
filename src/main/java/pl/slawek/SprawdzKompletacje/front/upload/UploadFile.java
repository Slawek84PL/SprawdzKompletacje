package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.backend.entity.DataService;
import pl.slawek.SprawdzKompletacje.backend.entity.ExcelReaderToDB;
import pl.slawek.SprawdzKompletacje.front.MainView;

import java.io.IOException;

@RolesAllowed({"ADMIN", "USER"})
@Route(value = "Upload", layout = MainView.class)
public
class UploadFile extends VerticalLayout {

    private final UploadFilesI18N i18N = new UploadFilesI18N();
    private final FileBuffer buffer = new FileBuffer();
    private TextField configName = new TextField("Podaj nazwę klienta");
    private final ExcelReaderToDB excelReaderToDB;
    private ImportConfigView configView;
    private Checkbox configCheckbox;
    private Upload upload;
    private final DataService dataService;
    private Button saveButton = new Button();



    public UploadFile(final ExcelReaderToDB excelReaderToDB, final DataService dataService) {
        this.excelReaderToDB = excelReaderToDB;
        this.dataService = dataService;
        createConfigCheckbox();
        createUpload();
        createView();
    }

    private void createUpload() {
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(10240);

        upload.setI18n(i18N);

        upload.addSucceededListener(event -> {

            // TODO: 2023-04-23 sptawdzić gdzie umieścić ten wyjątek i zmiane FileBuffer na MemoryBuffer
            try {

                if (configCheckbox.getValue()) {
                    configView = new ImportConfigView(dataService);
                    configView.importHeaders(buffer.getFileData().getFile());
                    configView.createView();
                    add(configView);
                } else {
                    excelReaderToDB.readExcelFile(buffer.getFileData().getFile(), buffer.getFileName());
                }

            } catch (IOException ignore) {
            }

        });
    }

    private void createConfigCheckbox() {
        configCheckbox = new Checkbox("Konfiguruj klienta");
        configCheckbox.setIndeterminate(false);

    }

    private void createView() {
        add(new HorizontalLayout(upload, configName), configCheckbox);
    }

}
