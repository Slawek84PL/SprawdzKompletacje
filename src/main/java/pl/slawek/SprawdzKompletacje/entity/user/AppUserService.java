package pl.slawek.SprawdzKompletacje.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<AppUser> findAllAppUser() {
        return appUserRepository.findAll();
    }

    public void saveNewUser(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        appUserRepository.save(user);
    }
}
