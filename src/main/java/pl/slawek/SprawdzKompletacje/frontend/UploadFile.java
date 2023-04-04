package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;

@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    public UploadFile() {
        createUpload();
    }

    private void createUpload() {
        Upload upload = new Upload();
        add(upload);
    }




}
