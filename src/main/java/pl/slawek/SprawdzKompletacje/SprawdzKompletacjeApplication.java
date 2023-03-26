package pl.slawek.SprawdzKompletacje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import pl.slawek.SprawdzKompletacje.file.FileList;
import pl.slawek.SprawdzKompletacje.skan.MainView;

import java.io.File;
import java.util.Scanner;

@SpringBootApplication
public class SprawdzKompletacjeApplication {


	public static void main(String[] args) {
		SpringApplication.run(SprawdzKompletacjeApplication.class, args);
	}

}
