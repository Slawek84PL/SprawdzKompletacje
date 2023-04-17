package pl.slawek.SprawdzKompletacje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SprawdzKompletacjeApplication {


	public static void main(String[] args) {
		SpringApplication.run(SprawdzKompletacjeApplication.class, args);
	}

}
