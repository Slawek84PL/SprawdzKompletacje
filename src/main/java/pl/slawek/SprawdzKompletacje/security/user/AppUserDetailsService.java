package pl.slawek.SprawdzKompletacje.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public
class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if (user != null) {
            return new AppUserDetails(user);
        }
        throw new UsernameNotFoundException("Nie znaleziono użytkownika " + username);
    }
}
