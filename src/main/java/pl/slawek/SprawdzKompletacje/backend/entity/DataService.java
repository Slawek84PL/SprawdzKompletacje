package pl.slawek.SprawdzKompletacje.backend.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.backend.entity.config.Config;
import pl.slawek.SprawdzKompletacje.backend.entity.config.ConfigService;
import pl.slawek.SprawdzKompletacje.backend.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.backend.entity.order.OrderService;
import pl.slawek.SprawdzKompletacje.backend.entity.product.Product;
import pl.slawek.SprawdzKompletacje.backend.entity.product.ProductService;
import pl.slawek.SprawdzKompletacje.backend.entity.product.scanned.ScannedPosition;
import pl.slawek.SprawdzKompletacje.backend.entity.product.scanned.ScannedPositionRepository;
import pl.slawek.SprawdzKompletacje.backend.security.SecurityService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataService {
    private final OrderService orderService;
    private final ProductService productService;
    private final ScannedPositionRepository positionRepository;
    private final SecurityService securityService;
    private final ConfigService configService;

    DataService(final OrderService orderService,
                final ProductService productService,
                final ScannedPositionRepository positionRepository,
                final SecurityService securityService, final ConfigService configService) {
        this.orderService = orderService;
        this.productService = productService;
        this.positionRepository = positionRepository;
        this.securityService = securityService;
        this.configService = configService;
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

    public void addProductsToOrder(final OrderNumber orderNumber, List<Product> products) {
        orderNumber.setImportUser(securityService.getAuthenticatedUser());
        orderNumber.setProducts(products);
        orderService.save(orderNumber);
    }

    public void saveConfig(Config config, final String configName) {
        config.setUser(securityService.getAuthenticatedUser());
        config.setConfigName(configName);
        configService.save(config);
    }

    public boolean isExistConfigName(String configName) {
        return configService.isExistConfigName(configName);
    }
}
