package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.time.LocalDateTime;
import java.util.List;

@Service
public
class OrderService {
    private final OrderRepository orderRepo;

    OrderService(final OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }


    public void addProductsToOrder(final OrderNumber orderNumber, List<Product> products) {
        orderNumber.setProducts(products);
        orderRepo.save(orderNumber);
    }


    public List<OrderNumber> findAllActiveOrders() {
        return orderRepo.findByIsFinishedFalse();
    }

    public void finishOrder(final String fileName) {
        OrderNumber orderNumber = orderRepo.findByFileName(fileName).get();
        orderNumber.setFinished(true);
        orderNumber.setFinishedDate(LocalDateTime.now());
        orderRepo.save(orderNumber);
    }

    public List<OrderNumber> findAllFinishedOrder() {
        return orderRepo.findByIsFinishedTrue();
    }

    public void save(final OrderNumber orderNumber) {
        orderRepo.save(orderNumber);
    }
}
