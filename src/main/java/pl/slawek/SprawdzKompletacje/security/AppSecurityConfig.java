package pl.slawek.SprawdzKompletacje.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.slawek.SprawdzKompletacje.front.LoginView;
import pl.slawek.SprawdzKompletacje.security.user.AppUserDetailsService;
import pl.slawek.SprawdzKompletacje.security.user.AppUserRepository;


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends VaadinWebSecurity {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
//        System.out.println("w addUser");
//        AppUser user = new AppUser();
//        user.setUsername("slawek");
//        user.setPassword(passwordEncoder().encode("pass"));
//        user.setRole(Role.ADMIN);
//        user.setEnabled(true);
//        repository.save(user);
    }

    @Override
    protected void configure(final WebSecurity web) throws Exception {
        super.configure(web);
    }

//    @Bean
//    JdbcUserDetailsManager users(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    @Bean
//    public UserDetailsManager userDetailsManager() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}pass")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password("{noop}pass")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
