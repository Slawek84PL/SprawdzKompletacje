package pl.slawek.SprawdzKompletacje.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.slawek.SprawdzKompletacje.entity.user.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
}