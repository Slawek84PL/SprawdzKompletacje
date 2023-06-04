package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.slawek.SprawdzKompletacje.entity.DataService;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RolesAllowed("ADMIN")
@Route(value = "Zamowienia", layout = MainView.class)
@PageTitle("Zamówienia")
public class Orders extends VerticalLayout {

    private final ComboBox<OrderNumber> fileSelector = new ComboBox<>("Wybierz zlecenie");
    private final TextField barcodeScanner = new TextField("Kod produktu");
    private final IntegerField quantityField = new IntegerField("Ilość pobranych sztuk");
    private final Button addButton = new Button("Zapisz");
    private final Button reloadButton = new Button("Załaduj zamówienie");
    private final Span status;
    private final Button finishFile = new Button("Zakończ sprawdzanie zamówienia");
    private final Div finishDiv = new Div();
    private final Button finishFileButton = new Button("Zakończ");
    private final Grid<Product> productsGrid = new Grid<>(Product.class, false);
    private List<Product> productList = new ArrayList<>();

    private final OrderService orderService;
    private final DataService dataService;

    public Orders(final OrderService orderService, final DataService dataService) {
        this.orderService = orderService;
        this.dataService = dataService;
        status = new Span();
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        fileSelector.setReadOnly(true);
        fileSelector.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                productList = fileSelector.getValue().getProducts();
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
        finishFileButton.setClassName("error");
        finishFileButton.addClickListener(event -> {
            orderService.finishOrder(fileSelector.getValue().getFileName());
            finishDiv.setVisible(false);
            clear();
            fileSelector.setReadOnly(true);
            fileSelector.clear();
            productsGrid.setVisible(false);
            barcodeScanner.setReadOnly(true);
            finishFile.setEnabled(false);
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
            dataService.updatePositionOnProduct(fileSelector.getValue() ,product, quantity);
            productList = fileSelector.getValue().getProducts();
            productsGrid.setItems(productList);
            clear();
        });

        reloadButton.setEnabled(true);
        reloadButton.addClickListener(event -> {
            fileSelector.setItems(orderService.findAllActiveOrders());
            fileSelector.setItemLabelGenerator(OrderNumber::getFileName);
            fileSelector.setReadOnly(false);
            productsGrid.setVisible(false);
            clear();
            barcodeScanner.setReadOnly(true);
            fileSelector.focus();
        });
        productsGrid.setVisible(false);
        productsGrid.addColumn(Product::getBarcode).setHeader("Kod produktu").setAutoWidth(true).setFlexGrow(1);
        productsGrid.addColumn(Product::getName).setHeader("Nazwa produktu").setAutoWidth(true).setFlexGrow(1);
        productsGrid.addColumn(Product::getQuantity).setHeader("Ilość zamówiona").setAutoWidth(true).setFlexGrow(1);
        productsGrid.addColumn(Product::getScannedQuantity).setHeader("Ilość zeskanowana").setAutoWidth(true).setFlexGrow(1);
        productsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        productsGrid.setEnabled(false);
//        productsGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        productsGrid.setPartNameGenerator(product -> {
            if(product.getQuantity() == product.getScannedQuantity()){
                return "green-background";
            }
            if (product.getQuantity() < product.getScannedQuantity()) {
                return "red-background";
            }
            return null;
        });
    }

    private void buildLayout() {
        HorizontalLayout hr = new HorizontalLayout();
        hr.add(reloadButton,finishFile, finishDiv);

        finishDiv.add(status, finishFileButton);
        finishDiv.setVisible(false);

        Div div = new Div();
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
