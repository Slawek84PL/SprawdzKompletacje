package pl.slawek.SprawdzKompletacje.backend.entity.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {
}