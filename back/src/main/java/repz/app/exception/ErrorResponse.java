package repz.app.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String details;
    private String path;
    private List<String> errors;
    private String timestamp;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now().format(FORMATTER);
    }

    public ErrorResponse(int status, String message, String details) {
        this();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(int status, String message, String details, String path, List<String> errors) {
        this(status, message, details);
        this.path = path;
        this.errors = errors;
    }

}
