package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.backend.entity.ExcelReaderToDB;

import java.io.IOException;

@RolesAllowed("ADMIN")
@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    private UploadFilesI18N i18N = new UploadFilesI18N();
    private final FileBuffer buffer = new FileBuffer();
    private final ExcelReaderToDB excelReaderToDB;
    public UploadFile(final ExcelReaderToDB excelReaderToDB) {
        this.excelReaderToDB = excelReaderToDB;
        createUpload();
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
