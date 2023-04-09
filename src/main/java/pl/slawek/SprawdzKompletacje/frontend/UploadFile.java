package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.slawek.SprawdzKompletacje.file.UploadFileService;

import java.io.InputStream;

@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {


    private final String destinationPath;
    private final FileBuffer buffer = new FileBuffer();
    private final UploadFileService uploadFileService = new UploadFileService();

    public UploadFile(@Value("${file.path}") String destinationPath) {
        this.destinationPath = destinationPath;
        createUpload();
    }

    private void createUpload() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(10240);

        upload.addSucceededListener(event -> {

            InputStream inputStream = buffer.getInputStream();
            uploadFileService.copyFile(buffer.getFileName(), inputStream, destinationPath);
        });

        add(upload);
    }




}
