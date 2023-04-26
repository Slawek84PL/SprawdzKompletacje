package pl.slawek.SprawdzKompletacje.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import pl.slawek.SprawdzKompletacje.entity.product.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate date;

    @OneToMany(mappedBy = "orderNumber", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private boolean isFinished = false;

}
