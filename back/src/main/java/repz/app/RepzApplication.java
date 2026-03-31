package repz.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@SpringBootApplication
public class RepzApplication {
	public static void main(String[] args) {
		SpringApplication.run(RepzApplication.class, args);
	}
}
