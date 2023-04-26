package pl.slawek.SprawdzKompletacje.entity.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.ProductRepository;

import java.util.List;

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
        List<OrderNumber> orders = orderRepo.findByIsFinishedFalse();
        return orders.stream()
                .map(OrderNumber::getFileName)
                .sorted().toList();

    }

    public long getIdByOrderNumber(final String orderNumber) {
        return orderRepo.findByFileName(orderNumber).get().getId();
    }

    public OrderNumber findOrderNumber(final String orderNumber) {
        return orderRepo.findByFileName(orderNumber).get();
    }

    public void finishOrder(final String fileName) {
        OrderNumber orderNumber = orderRepo.findByFileName(fileName).get();
        orderNumber.setFinished(true);
        orderRepo.save(orderNumber);
    }
}
