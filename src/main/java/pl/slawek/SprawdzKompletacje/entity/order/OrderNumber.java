package pl.slawek.SprawdzKompletacje.entity.order;

import jakarta.persistence.*;
import lombok.Data;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order_numbers")
public class OrderNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String fileName;

    @Column(nullable = false)
    private LocalDateTime importDate;

    @OneToMany(mappedBy = "orderNumber", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private boolean isFinished = false;

    private LocalDateTime finishedDate;

    @PrePersist
    private void setImportDate() {
        this.importDate = LocalDateTime.now();
    }
}
