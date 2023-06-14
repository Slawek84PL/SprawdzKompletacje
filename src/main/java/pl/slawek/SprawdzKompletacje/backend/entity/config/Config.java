package pl.slawek.SprawdzKompletacje.backend.entity.config;

import jakarta.persistence.*;
import lombok.Data;
import pl.slawek.SprawdzKompletacje.backend.entity.user.AppUser;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String configName;

    private String barcodeColumn;

    private String nameColumn;

    private String quantityColumn;

    private LocalDateTime createDate;

    @OneToOne
    private AppUser user;

    @PostPersist
    private void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }
}
