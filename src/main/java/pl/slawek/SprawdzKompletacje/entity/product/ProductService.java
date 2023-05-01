package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPositionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final OrderService orderService;
    private final ScannedPositionRepository positionRepository;

    public ProductService(final ProductRepository productRepo, final OrderService orderService, final ScannedPositionRepository positionRepository) {
        this.productRepo = productRepo;
        this.orderService = orderService;
        this.positionRepository = positionRepository;
    }

    public List<Product> getAllProductForOrderNumber(final String orderNumber) {
        long orderId = orderService.getIdByOrderNumber(orderNumber);
        return productRepo.findByOrderNumber_Id(orderId);
    }

    @Transactional
    public void updatePositionOnProduct(final Product product, int scannedQuantity) {
        product.setScannedQuantity(product.getScannedQuantity() + scannedQuantity);

        ScannedPosition scannedPosition = new ScannedPosition();
        scannedPosition.setScannedTime(LocalDateTime.now());
        scannedPosition.setScannedQuantity(scannedQuantity);
        scannedPosition.setProduct(product);

        positionRepository.save(scannedPosition);
        productRepo.save(product);
    }
}
