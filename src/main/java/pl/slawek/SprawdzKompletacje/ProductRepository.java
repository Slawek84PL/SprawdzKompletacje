package pl.slawek.SprawdzKompletacje;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.slawek.SprawdzKompletacje.skan.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}