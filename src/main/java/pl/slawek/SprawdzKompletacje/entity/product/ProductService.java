package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(final ProductRepository productRepo) {
        this.productRepo = productRepo;

    }


}
