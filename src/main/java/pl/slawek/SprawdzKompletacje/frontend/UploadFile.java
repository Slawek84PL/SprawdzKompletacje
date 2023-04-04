package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.router.Route;

import java.io.InputStream;

@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    private final FileBuffer buffer = new FileBuffer();

    public UploadFile() {
        createUpload();
    }

    private void createUpload() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(1024);

        upload.addSucceededListener(event -> {

            InputStream inputStream = buffer.getInputStream();
        });

        add(upload);
    }




}
