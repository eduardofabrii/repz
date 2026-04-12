package repz.app;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class RepzApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepzApplication.class, args);
    }
}
