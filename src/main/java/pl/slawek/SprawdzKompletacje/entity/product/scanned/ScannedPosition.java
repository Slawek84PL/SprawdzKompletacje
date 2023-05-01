package pl.slawek.SprawdzKompletacje.entity.product.scanned;

import jakarta.persistence.*;
import lombok.Data;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scanned_positions")
public class ScannedPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime scannedTime;

    private int ScannedQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

}
