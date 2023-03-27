package pl.slawek.SprawdzKompletacje.skan;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

@CssImport("./styles/my-grid-styles.css")
@Component
@Route("/")
public class MainView extends VerticalLayout {

    private final ComboBox<String> fileSelector = new ComboBox<>("Wybierz zlecenie");
    private final TextField barcodeScanner = new TextField("Kod produktu");
    private final TextField quantityField = new TextField("Ilość pobranych sztuk");
    private final Button addButton = new Button("Zapisz");
    private final Button reloadButton = new Button("Odśwież");
    private final Grid<Product> productsGrid = new Grid<>(Product.class, false);
    private File selectedFile;
    private List<Product> productList = new ArrayList<>();
    private final FileLister fileLister = new FileLister();
    private final ExcelReader excelReader = new ExcelReader();
    private final ExcelWriter excelWriter = new ExcelWriter();

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
                            Notification.show("Znaleziono produkt: " + product.getName());
                            quantityField.setValue(String.valueOf(product.getQuantity() - product.getScannedQuantity()));
                            quantityField.setReadOnly(false);
                            productsGrid.select(product);
                            addButton.setEnabled(true);
                        },
                        () -> Notification.show("Nie znaleziono produktu")
                );
            }
        });

        quantityField.setReadOnly(true);
        addButton.setEnabled(false);
        addButton.addClickListener(event -> {
            int quantity = Integer.parseInt(quantityField.getValue());
            Product product = productsGrid.getSelectedItems().iterator().next();
            product.setScannedQuantity(product.getScannedQuantity() + quantity);
            excelWriter.updateProduct(selectedFile, product);
            productList = excelReader.readProductsFromExcel(selectedFile);
            productsGrid.setItems(productList);
            clear();

        });

        reloadButton.setEnabled(true);
        reloadButton.addClickListener(event -> {
            fileSelector.setItems(fileLister.getExcelFileNames());
            // TODO: 2023-03-26 czyszczenie grid 
            clear();
            barcodeScanner.setReadOnly(true);
            fileSelector.focus();
                });

        productsGrid.addColumn(Product::getBarcode).setHeader("Kod produktu").setAutoWidth(true);
        productsGrid.addColumn(Product::getName).setHeader("Nazwa produktu").setAutoWidth(true);
        productsGrid.addColumn(Product::getQuantity).setHeader("Ilość zamówiona").setAutoWidth(true);
        productsGrid.addColumn(Product::getScannedQuantity).setHeader("Ilość zeskanowana").setAutoWidth(true);
        productsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        productsGrid.setPartNameGenerator(product -> {
            if(product.getQuantity() == product.getScannedQuantity()){
                return "green-background ";
            }
            if (product.getQuantity() < product.getScannedQuantity()) {
                return "red-background ";
            }
            return null;
        });
    }

    private void buildLayout() {
        HorizontalLayout hr = new HorizontalLayout();
        Div div = new Div();
        div.add(fileSelector, reloadButton);
        hr.add(div, barcodeScanner, quantityField);
        add(hr, addButton, productsGrid);
    }

    private void clear(){
        addButton.setEnabled(false);
        quantityField.setValue("");
        quantityField.setReadOnly(true);
        barcodeScanner.setValue("");
        barcodeScanner.focus();
    }

}
