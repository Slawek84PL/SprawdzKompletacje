package pl.slawek.SprawdzKompletacje.backend.entity.product.scanned;

import jakarta.persistence.*;
import lombok.*;
import pl.slawek.SprawdzKompletacje.backend.entity.product.Product;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
@Table(name = "scanned_positions")
public class ScannedPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime scannedTime;

    private int ScannedQuantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
