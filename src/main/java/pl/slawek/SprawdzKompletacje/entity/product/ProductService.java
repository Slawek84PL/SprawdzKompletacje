package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.stereotype.Service;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final OrderService orderService;

    public ProductService(final ProductRepository productRepo, final OrderService orderService) {
        this.productRepo = productRepo;
        this.orderService = orderService;
    }

    public void addProduct(Product product) {
        productRepo.save(product);
    }

    public List<Product> getAllProductForOrderNumber(final String orderNumber) {
        long orderId = orderService.getIdByOrderNumber(orderNumber);
        return productRepo.findByOrderNumber_Id(orderId);
    }

    public void updateProduct(final String fileName, final Product product) {
        productRepo.updateProduct(product.getScannedQuantity(), product.getId(), orderService.findOrderNumber(fileName));
    }
}
