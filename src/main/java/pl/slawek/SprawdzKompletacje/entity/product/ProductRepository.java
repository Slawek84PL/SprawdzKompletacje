package pl.slawek.SprawdzKompletacje.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.scannedQuantity = ?1 where p.id = ?2 and p.orderNumber = ?3")
    void updateProduct(int scannedQuantity, Long id, OrderNumber orderNumber);
    List<Product> findByOrderNumber_Id(Long id);
}