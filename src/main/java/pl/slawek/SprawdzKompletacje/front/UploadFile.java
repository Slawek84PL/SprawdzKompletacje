package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.backend.entity.ExcelReaderToDB;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigColumnReader;

import java.io.IOException;
import java.util.Map;

@RolesAllowed("ADMIN")
@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    private UploadFilesI18N i18N = new UploadFilesI18N();
    private final FileBuffer buffer = new FileBuffer();
    private final ExcelReaderToDB excelReaderToDB;
    private final ConfigColumnReader columnReader;
    public UploadFile(final ExcelReaderToDB excelReaderToDB, ConfigColumnReader columnReader) {
        this.excelReaderToDB = excelReaderToDB;
        this.columnReader = columnReader;
        createUpload();
        createUploadToConfig();
    }

    private void createUploadToConfig() {
        Upload uploadToConfig = new Upload(buffer);
        uploadToConfig.setAcceptedFileTypes(".xlsx");
        uploadToConfig.setMaxFiles(1);
        uploadToConfig.setMaxFileSize(10240);

        uploadToConfig.setI18n(i18N);

        uploadToConfig.addSucceededListener(event -> {

            // TODO: 2023-04-23 sptawdzić gdzie umieścić ten wyjątek i zmiane FileBuffer na MemoryBuffer
            try {
                columnReader.readExcelFile(buffer.getFileData().getFile());
            } catch (IOException ignore) {
            }

        });

        H1 h1 = new H1("konfiguracja");

        add(h1, uploadToConfig);
    }

    private void createUpload() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(10240);

        upload.setI18n(i18N);

        upload.addSucceededListener(event -> {

            // TODO: 2023-04-23 sptawdzić gdzie umieścić ten wyjątek i zmiane FileBuffer na MemoryBuffer
            try {
                excelReaderToDB.readExcelFile(buffer.getFileData().getFile(), buffer.getFileName());
            } catch (IOException ignore) {
            }

        });

        add(upload);
    }




}
