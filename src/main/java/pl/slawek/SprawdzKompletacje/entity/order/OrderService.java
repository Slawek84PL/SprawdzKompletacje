package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public
class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepository;

    OrderService(final OrderRepository orderRepo, final ProductRepository productRepository) {
        this.orderRepo = orderRepo;
        this.productRepository = productRepository;
    }


    public void addProductToOrder(final OrderNumber orderNumber, List<Product> products) {
        orderNumber.setProducts(products);
        orderRepo.save(orderNumber);

    }

    public List<String> findAll() {
        List<OrderNumber> orders = orderRepo.findAll();
        return orders.stream().
                map(OrderNumber::getFileName)
                .sorted().toList();

    }

    public long findOrderNumber(final String orderNumber) {
        return orderRepo.findByFileName(orderNumber).get().getId();
    }
}
