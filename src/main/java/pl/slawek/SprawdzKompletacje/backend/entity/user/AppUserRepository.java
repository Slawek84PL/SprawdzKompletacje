package pl.slawek.SprawdzKompletacje.backend.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
}