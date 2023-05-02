package pl.slawek.SprawdzKompletacje.entity;

import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.ProductService;

import java.util.List;

@Service
public class DataService {
    private final OrderService orderService;
    private final ProductService productService;

    DataService(final OrderService orderService, final ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

}
