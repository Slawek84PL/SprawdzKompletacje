package pl.slawek.SprawdzKompletacje.security.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public
class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> findAllAppUser() {
        return appUserRepository.findAll();
    }
}
