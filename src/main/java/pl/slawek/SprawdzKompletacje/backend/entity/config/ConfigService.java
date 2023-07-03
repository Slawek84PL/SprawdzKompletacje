package pl.slawek.SprawdzKompletacje.backend.entity.config;

import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final ConfigRepository repository;

    ConfigService(final ConfigRepository repository) {
        this.repository = repository;
    }

    public void save(Config config) {
        repository.save(config);

    }

    public boolean isExistConfigName(String configName) {
        return repository.existsByConfigName(configName);
    }
}
