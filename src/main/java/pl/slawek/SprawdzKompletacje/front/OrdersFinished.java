package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.entity.DataService;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;
import pl.slawek.SprawdzKompletacje.front.datePicker.RangeDatePicker;

import java.util.ArrayList;
import java.util.List;

@RolesAllowed("ADMIN")
@Route(value = "Zakonczone", layout = MainView.class)
@PageTitle("Zakończone zamówienia")
class OrdersFinished extends Div {

    private final Grid<OrderNumber> orderGrid = new Grid<>(OrderNumber.class, false);
    private final Grid<Product> productGrid = new Grid<>(Product.class,false);
    private final Grid<ScannedPosition> detailsGrid = new Grid<>(ScannedPosition.class,false);
    private final RangeDatePicker datePicker = new RangeDatePicker();
    private final Button findButton = new Button("Znajdź", VaadinIcon.SEARCH.create());
    private final DataService dataService;
    private final OrderService orderService;
    private List<Product> productList = new ArrayList<>();
    private List<OrderNumber> orderList = new ArrayList<>();

    @Autowired
    OrdersFinished(final DataService dataService, final OrderService orderService) {
        this.dataService = dataService;
        this.orderService = orderService;
        configureOrderGrid();
        configureProductGrid();
        configureDetailsGrid();
        configureFindButton();
        addComponents();
    }

    private void configureFindButton() {
        findButton.addClickListener(event -> {
            orderList = orderService.findByIsFinishedTrueAndImportDateBetween(datePicker.getStartDate().getValue(), datePicker.getFinishDate().getValue());
            System.out.println(orderList);
            orderGrid.setItems(orderList);
            productGrid.setVisible(false);
            detailsGrid.setVisible(false);
        });
    }

    private void configureOrderGrid() {
        orderGrid.addColumn(OrderNumber::getFileName).setHeader("Numer Zamówienia");
        orderGrid.addColumn(new LocalDateTimeRenderer<>(OrderNumber::getImportDate, "yyyy-MM-dd HH:mm")).setHeader("Data importu").setWidth("100px");
        orderGrid.addColumn(new LocalDateTimeRenderer<>(OrderNumber::getFinishedDate, "yyyy-MM-dd HH:mm")).setHeader("Data zakończenia");
        orderGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        formatMyGrid(orderGrid);
        orderGrid.setVisible(true);
        orderGrid.addItemClickListener(e -> {
            productList = e.getItem().getProducts();
            productGrid.setItems(productList);
            productGrid.setVisible(true);
            detailsGrid.setVisible(true);
            detailsGrid.setEnabled(false);
        });
    }

    private void configureProductGrid() {
        productGrid.addColumn(Product::getName).setHeader("Nazwa productu");
        productGrid.addColumn(Product::getBarcode).setHeader("Kod produktu");
        productGrid.addColumn(Product::getQuantity).setHeader("Ilość zamówiona");
        productGrid.addColumn(Product::getScannedQuantity).setHeader("Ilość zeskanowana");
        productGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        formatMyGrid(productGrid);
        productGrid.addItemClickListener(e -> {
            detailsGrid.setItems(e.getItem().getScannedPositionList());
            detailsGrid.setEnabled(true);
        });
    }

    private void configureDetailsGrid() {
        formatMyGrid(detailsGrid);
        detailsGrid.setSelectionMode(Grid.SelectionMode.NONE);
        detailsGrid.addColumn(new LocalDateTimeRenderer<>(ScannedPosition::getScannedTime, "yyyy-MM-dd HH:mm")).setHeader("Data skanowania");
        detailsGrid.addColumn(ScannedPosition::getScannedQuantity).setHeader("Ilość zatwierdzonych sztuk");
    }

    private void formatMyGrid(Grid grid) {
        grid.setVisible(false);
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);
    }

    private void addComponents() {
        HorizontalLayout hlGrid = new HorizontalLayout();
        hlGrid.setAlignItems(FlexComponent.Alignment.CENTER);
        hlGrid.add(productGrid,detailsGrid);
        add(datePicker, findButton, orderGrid, hlGrid);
    }
}
