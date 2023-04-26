package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.ProductService;
import pl.slawek.SprawdzKompletacje.file.FinishFileService;
import pl.slawek.SprawdzKompletacje.file.ExcelReader;
import pl.slawek.SprawdzKompletacje.file.ExcelWriter;

import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@CssImport("./styles/my-grid-styles.css")
@Route(value = "Zamówienia", layout = MainView.class)
@PageTitle("Zamówienia")
public class Orders extends VerticalLayout {

    private final ComboBox<String> fileSelector = new ComboBox<>("Wybierz zlecenie");
    private final TextField barcodeScanner = new TextField("Kod produktu");
    private final IntegerField quantityField = new IntegerField("Ilość pobranych sztuk");
    private final Button addButton = new Button("Zapisz");
    private final Button reloadButton = new Button("Załaduj zamówienie");
    private final Span status;
    private final Button finishFile = new Button("Zakończ sprawdzanie zamówienia");
    private final Div finishDiv = new Div();
    private final Button finishFileButton = new Button("Zakończ");
    private final Grid<Product> productsGrid = new Grid<>(Product.class, false);
    private String selectedFile;
    private List<Product> productList = new ArrayList<>();
    private final ExcelWriter excelWriter;
    private final FinishFileService finishFileService;

    private final OrderService orderService;
    private final ProductService productService;

    public Orders(final ExcelWriter excelWriter, final FinishFileService finishFileService, final OrderService orderService, final ProductService productService) {
        this.finishFileService = finishFileService;
        this.orderService = orderService;
        this.excelWriter = excelWriter;
        this.productService = productService;
        status = new Span();
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        fileSelector.setReadOnly(true);
        fileSelector.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                selectedFile = event.getValue();
                productList = productService.getAllProductForOrderNumber(selectedFile);
                productsGrid.setItems(productList);
                productsGrid.setVisible(true);
                clear();
                barcodeScanner.setReadOnly(false);
                finishFile.setEnabled(true);
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
        finishFile.setEnabled(false);
        finishFile.addClickListener(event -> {
            finishDiv.setVisible(true);
            status.setText("Operacja jest nieodwracalna. Czy na pewno chcesz zakończyć sprawdzanie?");
        });

        finishFileButton.setEnabled(true);
        finishFileButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        finishFileButton.addClickListener(event -> {
            finishFileService.moveAndRenameFile(fileSelector.getValue());
            finishDiv.setVisible(false);
            clear();
            fileSelector.setReadOnly(true);
            fileSelector.clear();
            barcodeScanner.setReadOnly(true);
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
            productService.updateProduct(selectedFile, product);
            productList = productService.getAllProductForOrderNumber(selectedFile);
            productsGrid.setItems(productList);
            clear();
        });

        reloadButton.setEnabled(true);
        reloadButton.addClickListener(event -> {
            fileSelector.setItems(orderService.findAll());
            fileSelector.setReadOnly(false);
            // TODO: 2023-03-26 czyszczenie grid
            productsGrid.setVisible(false);
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
        HorizontalLayout hr = new HorizontalLayout();
        hr.add(reloadButton,finishFile, finishDiv);
        finishDiv.add(status, finishFileButton);
        finishDiv.setVisible(false);
        div.add(fileSelector, barcodeScanner, quantityField);
        add(hr, div, addButton, productsGrid);
    }

    private void clear(){
        addButton.setEnabled(false);
        quantityField.setValue(0);
        quantityField.setReadOnly(true);
        barcodeScanner.setValue("");
        barcodeScanner.focus();
    }

}
