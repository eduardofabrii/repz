package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização do próprio perfil do aluno")
public record AlunoMeUpdateRequest(

        @Schema(description = "Nome do aluno", example = "João Silva")
        String nome,

        @Schema(description = "Telefone do aluno", example = "11999999999")
        String telefone,

        @Schema(description = "URL da foto de perfil", example = "https://cdn.repz.com/foto.jpg")
        String fotoUrl,

        @Schema(description = "Nova senha", example = "novaSenha123")
        String senha
) {}
