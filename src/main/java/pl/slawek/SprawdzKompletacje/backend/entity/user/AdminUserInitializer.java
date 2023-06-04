package pl.slawek.SprawdzKompletacje.backend.entity.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class AdminUserInitializer implements CommandLineRunner {

    private final AppUserService service;
    private final String userPass;

    AdminUserInitializer(final AppUserService service, @Value("${admin.pass}") final String userPass) {
        this.service = service;
        this.userPass = userPass;
    }

    @Override
    public void run(final String... args) throws Exception {
        if (!service.existByUsername()) {
            AppUser user = new AppUser();
            user.setUsername("ADMIN");
            user.setFirsName("Sławomir");
            user.setLastName("Pytlarz");
            user.setRole(Role.ADMIN);
            user.setPassword(userPass);
            user.setEnabled(true);
            service.saveFirstAdmin(user);
        }
    }
}
