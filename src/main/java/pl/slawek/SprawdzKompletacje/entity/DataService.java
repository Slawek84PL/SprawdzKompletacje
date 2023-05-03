package pl.slawek.SprawdzKompletacje.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.product.ProductService;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPositionRepository;

import java.time.LocalDateTime;

@Service
public class DataService {
    private final OrderService orderService;
    private final ProductService productService;
    private final ScannedPositionRepository positionRepository;

    DataService(final OrderService orderService, final ProductService productService, final ScannedPositionRepository positionRepository) {
        this.orderService = orderService;
        this.productService = productService;
        this.positionRepository = positionRepository;
    }

    @Transactional
    public void updatePositionOnProduct(final OrderNumber orderNumber, final Product product, int scannedQuantity) {
        ScannedPosition scannedPosition = new ScannedPosition();
        scannedPosition.setScannedTime(LocalDateTime.now());
        scannedPosition.setScannedQuantity(scannedQuantity);

        product.setScannedQuantity(product.getScannedQuantity() + scannedQuantity);
        scannedPosition.setProduct(product);
        orderService.save(orderNumber);
        positionRepository.save(scannedPosition);
    }

}
