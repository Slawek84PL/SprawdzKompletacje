package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.entity.DataService;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Zakończone", layout = MainView.class)
@PageTitle("Zakończone zamówienia")
class OrdersFinished extends Div {
    private final Grid<Product> orderNumberGrid = new Grid<>();
    private final DataService dataService;
    private final OrderService orderService;
    private List<Product> productList = new ArrayList<>();
    private List<OrderNumber> orderNumberList = new ArrayList<>();

    @Autowired
    OrdersFinished(final DataService dataService, final OrderService orderService) {
        this.dataService = dataService;
        this.orderService = orderService;
        dataInitialize();
        configureTreeGrid();
        addComponents();
    }

    private void dataInitialize() {
        orderNumberList = orderService.findAllFinishedOrder();
        System.out.println(orderNumberList);

    }

    private void addComponents() {
        add(orderNumberGrid);
    }

    private void configureTreeGrid() {
        orderNumberGrid.setItems((productList));
        orderNumberGrid.addColumn(Product::getOrderNumber).setHeader("Numer Zamówienia");
        orderNumberGrid.addColumn(Product::getName).setHeader("Pozycja Zamówienia");
        orderNumberGrid.addColumn(Product::getScannedQuantity);
    }
}
