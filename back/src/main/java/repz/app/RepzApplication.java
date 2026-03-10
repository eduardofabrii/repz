package repz.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import repz.app.domain.user.User;
import repz.app.domain.user.UserRole;
import repz.app.repository.UserRepository;

@AllArgsConstructor
@SpringBootApplication
public class RepzApplication implements CommandLineRunner{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(RepzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User usuario1 = new User();
		usuario1.setName("Teste");
		usuario1.setEmail("teste@gmail.com");
		usuario1.setPassword(passwordEncoder.encode("teste"));
		usuario1.setRole(UserRole.ADMIN);
		usuario1.setActive(true);

		List<User> usuarios = Arrays.asList(
			usuario1
		);
		
		userRepository.saveAll(usuarios);
	}
}
