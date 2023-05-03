package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.order.OrderRepository;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPositionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final ScannedPositionRepository positionRepository;
    private final OrderRepository orderRepository;

    public ProductService(final ProductRepository productRepo, final ScannedPositionRepository positionRepository, final OrderRepository orderRepository) {
        this.productRepo = productRepo;
        this.positionRepository = positionRepository;
        this.orderRepository = orderRepository;
    }



    public List<Product> getProductByOrderId(long id) {
        return productRepo.findByOrderNumber_Id(id);

    }
}
