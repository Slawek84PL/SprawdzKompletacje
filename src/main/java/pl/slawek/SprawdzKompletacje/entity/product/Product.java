package pl.slawek.SprawdzKompletacje.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.slawek.SprawdzKompletacje.entity.order.OrderNumber;


@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "order_number")
    private OrderNumber orderNumber;

}
