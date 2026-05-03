package repz.app.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${api.password-reset.base-url}")
    private String baseUrl;

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Repz — Recuperação de senha");
        message.setText("""
                Olá,

                Você solicitou a recuperação de senha da sua conta Repz.
                Use o código abaixo para redefinir sua senha (válido por 30 minutos):

                  %s

                Digite esse código no aplicativo para criar uma nova senha.
                Se não foi você, ignore este e-mail.
                """.formatted(resetToken));

        mailSender.send(message);
    }
}
