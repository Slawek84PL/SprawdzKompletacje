package pl.slawek.SprawdzKompletacje.front.upload;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.backend.entity.ExcelReaderToDB;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Config;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigColumnReader;
import pl.slawek.SprawdzKompletacje.front.MainView;

import java.io.IOException;
import java.util.List;

@RolesAllowed({"ADMIN", "USER"})
@Route(value = "Upload", layout = MainView.class)
public
class UploadFile extends VerticalLayout {

    private final UploadFilesI18N i18N = new UploadFilesI18N();
    private final FileBuffer buffer = new FileBuffer();
    private final ExcelReaderToDB excelReaderToDB;
    private final ConfigColumnReader columnReader;

    private Checkbox config;
    private Upload upload;
    private Grid<Config> configGrid = new Grid<>(Config.class, true);
    private List<Config> headers;

    public UploadFile(final ExcelReaderToDB excelReaderToDB, ConfigColumnReader columnReader) {
        this.excelReaderToDB = excelReaderToDB;
        this.columnReader = columnReader;
        createConfigCheckbox();
        createUpload();
        createView();
    }

    private void createGrid() {
        configGrid.setItems(headers);
        add(configGrid);
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
                    headers = columnReader.readExcelFile(buffer.getFileData().getFile());
                    createGrid();
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
