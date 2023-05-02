package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(final ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
    public void updatePositionOnProduct(final Product product, int scannedQuantity) {

        ScannedPosition scannedPosition = new ScannedPosition();
        scannedPosition.setScannedTime(LocalDateTime.now());
        scannedPosition.setScannedQuantity(scannedQuantity);
        scannedPosition.setProduct(product);

        product.setScannedQuantity(product.getScannedQuantity() + scannedQuantity);
        product.getScannedPositionList().add(scannedPosition);

        productRepo.save(product);
    }

    public List<Product> getProductByOrderId(long id) {
        return productRepo.findByOrderNumber_Id(id);

    }
}
