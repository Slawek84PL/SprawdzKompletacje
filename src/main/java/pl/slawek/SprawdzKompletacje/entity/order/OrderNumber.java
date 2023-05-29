package pl.slawek.SprawdzKompletacje.entity.order;

import jakarta.persistence.*;
import lombok.*;
import pl.slawek.SprawdzKompletacje.entity.product.Product;
import pl.slawek.SprawdzKompletacje.entity.user.AppUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
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
    private LocalDateTime importDate;

    @OneToOne
    private AppUser importUser;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderNumber", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private boolean isFinished = false;

    private LocalDateTime finishedDate;

    @OneToOne
    private AppUser finishedUser;

    @PrePersist
    private void setImportDate() {
        this.importDate = LocalDateTime.now();
    }


}
