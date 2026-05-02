package repz.app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tokens retornados após autenticação")
public record LoginResponseDTO(
        @Schema(description = "Token JWT de acesso", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token usado para renovar o acesso", example = "eyJhbGciOiJIUzI1NiJ9...")
        String refreshToken
) {
}
