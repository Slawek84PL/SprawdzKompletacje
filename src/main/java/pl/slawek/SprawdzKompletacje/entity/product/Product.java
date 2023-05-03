package pl.slawek.SprawdzKompletacje.entity.product;

import jakarta.persistence.*;
import lombok.*;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;
import pl.slawek.SprawdzKompletacje.entity.product.scanned.ScannedPosition;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number_id")
    private OrderNumber orderNumber;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScannedPosition> scannedPositionList = new ArrayList<>();

}
