package repz.app.service.email;

public interface EmailService {

    void sendPasswordResetEmail(String to, String resetToken);
}
