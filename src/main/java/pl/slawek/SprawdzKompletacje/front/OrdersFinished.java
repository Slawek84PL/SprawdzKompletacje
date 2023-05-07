package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.entity.DataService;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Zakonczone", layout = MainView.class)
@PageTitle("Zakończone zamówienia")
class OrdersFinished extends Div {

    private final Grid<OrderNumber> orderGrid = new Grid<>(OrderNumber.class, false);
    private final Grid<Product> productGrid = new Grid<>(Product.class,false);
    private final Grid<ScannedPosition> detailsGrid = new Grid<>(ScannedPosition.class,false);
    private final DataService dataService;
    private final OrderService orderService;
    private List<Product> productList = new ArrayList<>();
    private List<OrderNumber> orderList = new ArrayList<>();

    @Autowired
    OrdersFinished(final DataService dataService, final OrderService orderService) {
        this.dataService = dataService;
        this.orderService = orderService;
        dataInitialize();
        configureOrderGrid();
        configureProductGrid();
        configureDetailsGrid();
        addComponents();
    }

    private void dataInitialize() {
        orderList = orderService.findAllFinishedOrder();
        for (OrderNumber o : orderList) {
            System.out.println(o.getProducts());
        }
        System.out.println(orderList);

    }

    private void configureOrderGrid() {
        orderGrid.addColumn(OrderNumber::getFileName).setHeader("Numer Zamówienia");
        orderGrid.addColumn(new LocalDateTimeRenderer<>(OrderNumber::getImportDate, "yyyy-MM-dd HH:mm")).setHeader("Data importu").setWidth("100px");
        orderGrid.addColumn(new LocalDateTimeRenderer<>(OrderNumber::getFinishedDate, "yyyy-MM-dd HH:mm")).setHeader("Data zakończenia");
        orderGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        formatMyGrid(orderGrid);
        orderGrid.setVisible(true);
        orderGrid.setItems(orderList);
        orderGrid.addItemClickListener(e -> {
            productList = e.getItem().getProducts();
            productGrid.setItems(productList);
            productGrid.setVisible(true);
            detailsGrid.setVisible(true);
        });
    }

    private void configureProductGrid() {
        productGrid.addColumn(Product::getName).setHeader("Nazwa productu");
        productGrid.addColumn(Product::getBarcode).setHeader("Kod prodyctu");
        productGrid.addColumn(Product::getQuantity).setHeader("Ilość zamówiona");
        productGrid.addColumn(Product::getScannedQuantity).setHeader("Ilość zeskanowana");
        productGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        formatMyGrid(productGrid);
        productGrid.addItemClickListener(e -> {
            detailsGrid.setItems(e.getItem().getScannedPositionList());
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
//        Div divGrid = new Div();
//        divGrid.add(orderGrid, productGrid,detailsGrid);
        final HorizontalLayout hr = new HorizontalLayout();
        hr.setAlignItems(FlexComponent.Alignment.CENTER);
        hr.add(productGrid,detailsGrid);
        add(orderGrid, hr);
    }
}
