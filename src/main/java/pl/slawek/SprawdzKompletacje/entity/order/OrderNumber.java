package pl.slawek.SprawdzKompletacje.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_numbers")
public class OrderNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String fileName;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "orderNumber", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private boolean isFinished = false;

    @PrePersist
    private void setDate() {
        this.date = LocalDateTime.now();
    }
}
