package pl.slawek.SprawdzKompletacje.skan;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;
import pl.slawek.SprawdzKompletacje.file.ExcelReader;
import pl.slawek.SprawdzKompletacje.file.ExcelWriter;
import pl.slawek.SprawdzKompletacje.file.FileLister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Route("/")
public class MainView extends VerticalLayout {

    private final ComboBox<String> fileSelector = new ComboBox<>("Wybierz zlecenie");
    private final TextField barcodeScanner = new TextField("Kod produktu");
    private final TextField quantityField = new TextField("Ilość pobranych sztuk");
    private final Button addButton = new Button("Zapisz");
    private final Grid<Product> productsGrid = new Grid<>(Product.class);
    private File selectedFile;
    private List<Product> productList = new ArrayList<>();
    private final FileLister fileLister = new FileLister();
    private final ExcelReader excelReader = new ExcelReader();

    public MainView() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        fileSelector.setItems(fileLister.getExcelFileNames());
        fileSelector.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectedFile = new File(event.getValue());
                productList = excelReader.readProductsFromExcel(selectedFile);
                productsGrid.setItems(productList);
                clear();
            }
        });

        barcodeScanner.addValueChangeListener(event -> {
            String barcode = event.getValue();
            if (!barcode.isEmpty()) {
                Optional<Product> optionalProduct = productList.stream()
                        .filter(product -> product.getBarcode().equals(barcode))
                        .findFirst();

                optionalProduct.ifPresentOrElse(
                        product -> {
                            Notification.show("Product found: " + product.getName());
                            quantityField.setValue(String.valueOf(product.getQuantity() - product.getScannedQuantity()));
                            quantityField.setReadOnly(false);
                            productsGrid.select(product);
                            addButton.setEnabled(true);
                        },
                        () -> Notification.show("Product not found")
                );
            }
        });

        quantityField.setReadOnly(true);
        addButton.setEnabled(false);
        addButton.addClickListener(event -> {
            int quantity = Integer.parseInt(quantityField.getValue());
            Product product = productsGrid.getSelectedItems().iterator().next();
            product.setScannedQuantity(product.getScannedQuantity() + quantity);
            ExcelWriter.updateProduct(selectedFile, product);
            productList = excelReader.readProductsFromExcel(selectedFile);
            productsGrid.setItems(productList);
            clear();

        });

        productsGrid.addColumn(Product::getBarcode);//.setHeader("Kod produktu");
        productsGrid.addColumn(Product::getName).setHeader("Nazwa produktu");
        productsGrid.addColumn(Product::getQuantity).setHeader("Ilość zamówiona");
        productsGrid.addColumn(Product::getScannedQuantity).setHeader("Ilość zeskanowana");
        productsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    private void buildLayout() {
        add(fileSelector, barcodeScanner, quantityField, addButton, productsGrid);
    }

    private void clear(){
        addButton.setEnabled(false);
        quantityField.setValue("");
        barcodeScanner.setValue("");
        barcodeScanner.focus();
    }

}
