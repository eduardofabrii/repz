package repz.app;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZoneId;
import java.util.TimeZone;

@AllArgsConstructor
@SpringBootApplication
@EnableScheduling
public class RepzApplication {
    public static void main(String[] args) {
        // Garante America/Sao_Paulo independente da TZ do SO/ambiente de deploy —
        // evita que LocalDateTime.now() (auditoria, check-in, tokens) grave em UTC.
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
        SpringApplication.run(RepzApplication.class, args);
    }
}
