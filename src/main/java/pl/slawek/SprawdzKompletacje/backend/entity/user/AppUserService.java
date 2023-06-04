package pl.slawek.SprawdzKompletacje.backend.entity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<AppUser> findAllAppUser() {
        return repository.findAll();
    }

    public void saveNewUser(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        repository.save(user);
    }

    boolean existByUsername() {
        return repository.existsByUsername("ADMIN");
    }

    void saveFirstAdmin(final AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }
}
