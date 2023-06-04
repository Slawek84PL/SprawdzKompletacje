package pl.slawek.SprawdzKompletacje.backend.entity.config;

import jakarta.persistence.*;
import lombok.Data;
import pl.slawek.SprawdzKompletacje.backend.entity.user.AppUser;

import java.util.Date;

@Data
@Entity
class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String viewName;

    private int columnNumber;

    private String columnNameHeader;

    private Date createDate;

    @OneToOne
    private AppUser user;
}
