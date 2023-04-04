package pl.slawek.SprawdzKompletacje.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import pl.slawek.SprawdzKompletacje.file.ExcelReader;
import pl.slawek.SprawdzKompletacje.file.ExcelWriter;
import pl.slawek.SprawdzKompletacje.file.FileLister;
import pl.slawek.SprawdzKompletacje.skan.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CssImport("./styles/my-grid-styles.css")
@Route(value = "Zamówienia", layout = MainView.class)
@PageTitle("Zamówienia")
public class Orders extends VerticalLayout {

    private final ComboBox<String> fileSelector = new ComboBox<>("Wybierz zlecenie");
    private final TextField barcodeScanner = new TextField("Kod produktu");
    private final IntegerField quantityField = new IntegerField("Ilość pobranych sztuk");
    private final Button addButton = new Button("Zapisz");
    private final Button reloadButton = new Button("Załaduj zlecenia");
    private final Grid<Product> productsGrid = new Grid<>(Product.class, false);
    private File selectedFile;
    private List<Product> productList = new ArrayList<>();
    private final FileLister fileLister;
    private final ExcelReader excelReader = new ExcelReader();
    private final ExcelWriter excelWriter = new ExcelWriter();
    @Value("${file.path}")
    private String filePath;
    public Orders() {
        fileLister = new FileLister();
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        fileSelector.setReadOnly(true);
        fileSelector.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectedFile = new File(event.getValue());
                productList = excelReader.readProductsFromExcel(filePath + selectedFile);
                productsGrid.setItems(productList);
                clear();
                barcodeScanner.setReadOnly(false);
            }
        });

        barcodeScanner.setReadOnly(true);
        barcodeScanner.addValueChangeListener(event -> {
            String barcode = event.getValue();
            if (!barcode.isEmpty()) {
                Optional<Product> optionalProduct = productList.stream()
                        .filter(product -> product.getBarcode().equals(barcode))
                        .findFirst();

                optionalProduct.ifPresentOrElse(
                        product -> {
                            Notification.show("Znaleziono produkt: " + product.getName());
                            quantityField.setValue(product.getQuantity() - product.getScannedQuantity());
                            quantityField.setReadOnly(false);
                            quantityField.focus();
                            productsGrid.select(product);
                        },
                        () -> Notification.show("Nie znaleziono produktu")
                );
            }
        });

        quantityField.setReadOnly(true);
        quantityField.addValueChangeListener(event -> {
            if (quantityField.isEmpty() || quantityField.getValue() == 0){
                addButton.setEnabled(false);

            } else {
                addButton.setEnabled(true);
                addButton.focus();
            }
        });

        addButton.setEnabled(false);
        addButton.addClickListener(event -> {
            int quantity = quantityField.getValue();
            Product product = productsGrid.getSelectedItems().iterator().next();
            product.setScannedQuantity(product.getScannedQuantity() + quantity);
            excelWriter.updateProduct(filePath + selectedFile, product);
            productList = excelReader.readProductsFromExcel(filePath + selectedFile);
            productsGrid.setItems(productList);
            clear();
        });

        reloadButton.setEnabled(true);
        reloadButton.addClickListener(event -> {
            fileSelector.setItems(fileLister.getExcelFileNames(filePath));
            fileSelector.setReadOnly(false);
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
        productsGrid.setEnabled(false);
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
        Div div = new Div();
        div.add(fileSelector, barcodeScanner, quantityField);
        add(reloadButton, div, addButton, productsGrid);
    }

    private void clear(){
        addButton.setEnabled(false);
        quantityField.setValue(0);
        quantityField.setReadOnly(true);
        barcodeScanner.setValue("");
        barcodeScanner.focus();
    }

}
