package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import pl.slawek.SprawdzKompletacje.file.CopyFileService;
import pl.slawek.SprawdzKompletacje.file.config.FileConfig;

import java.io.InputStream;

@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    private final FileBuffer buffer = new FileBuffer();
    private final CopyFileService copyFileService;
    public UploadFile(final FileConfig fileConfig) {
        this.copyFileService = new CopyFileService(fileConfig);
        createUpload();
    }

    private void createUpload() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(10240);

        upload.addSucceededListener(event -> {

            InputStream inputStream = buffer.getInputStream();
            copyFileService.copyFile(buffer.getFileName(), inputStream);
        });

        add(upload);
    }




}
