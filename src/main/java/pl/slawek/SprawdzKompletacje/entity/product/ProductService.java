package pl.slawek.SprawdzKompletacje.entity.product;

import org.apache.poi.ss.usermodel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(final ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(Product product) {
        productRepo.save(product);
    }


}
