package pl.slawek.SprawdzKompletacje.entity.product;

import jakarta.persistence.*;
import lombok.Data;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String barcode;
    private String name;
    private int quantity;
    private int scannedQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_number_id")
    private OrderNumber orderNumber;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScannedPosition> scannedPositionList = new ArrayList<>();
}
