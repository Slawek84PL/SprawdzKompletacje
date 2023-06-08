package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.backend.entity.ExcelReaderToDB;
import pl.slawek.SprawdzKompletacje.front.MainView;

import java.io.IOException;

@RolesAllowed({"ADMIN", "USER"})
@Route(value = "Upload", layout = MainView.class)
public
class UploadFile extends VerticalLayout {

    private final UploadFilesI18N i18N = new UploadFilesI18N();
    private final FileBuffer buffer = new FileBuffer();
    private final ExcelReaderToDB excelReaderToDB;
    private ImportConfigView configView;
    private Checkbox config;
    private Upload upload;


    public UploadFile(final ExcelReaderToDB excelReaderToDB) {
        this.excelReaderToDB = excelReaderToDB;
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

                if (config.getValue()) {
                    configView = new ImportConfigView(buffer.getFileData().getFile());
                    add(configView);
                } else {
                    excelReaderToDB.readExcelFile(buffer.getFileData().getFile(), buffer.getFileName());
                }

            } catch (IOException ignore) {
            }

        });
    }

    private void createConfigCheckbox() {
        config = new Checkbox("Konfiguruj klienta");
        config.setIndeterminate(false);

    }

    private void createView() {
        add(upload, config);
    }

}
