package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import pl.slawek.SprawdzKompletacje.file.CopyFileService;
import pl.slawek.SprawdzKompletacje.file.config.PathFileConfig;
import pl.slawek.SprawdzKompletacje.entity.product.ProductService;

@Route(value = "Upload", layout = MainView.class)
class UploadFile extends VerticalLayout {

    private final FileBuffer buffer = new FileBuffer();
    private final CopyFileService copyFileService;
    private final ProductService productService;

    public UploadFile(final PathFileConfig pathFileConfig, final ProductService productService) {
        this.copyFileService = new CopyFileService(pathFileConfig);
        this.productService = productService;
        createUpload();
    }

    private void createUpload() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".xlsx");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(10240);

        upload.addSucceededListener(event -> {
            productService.readExcel(buffer.getFileData().getFile().getAbsolutePath());
        });

        add(upload);
    }




}
