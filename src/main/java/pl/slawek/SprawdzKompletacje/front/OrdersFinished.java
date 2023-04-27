package pl.slawek.SprawdzKompletacje.front;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(value = "Zakończone", layout = MainView.class)
@PageTitle("Zakończone zamówienia")
class OrdersFinished extends Div {
    private final TreeGrid<OrderNumber> orderNumberGrid = new TreeGrid<>();
    private final OrderService orderService;
    private List<OrderNumber> orderNumbers = new ArrayList<>();

    @Autowired
    OrdersFinished(final OrderService orderService) {
        this.orderService = orderService;
        configureTreeGrid();
        addComponents();
    }

    private void addComponents() {
        add(orderNumberGrid);
    }

    private void configureTreeGrid() {
        orderNumbers = orderService.findAllFinishedOrder();
    }
}
