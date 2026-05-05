package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para matrícula de aluno em academia")
public record AlunoCreateRequest(

        @Schema(description = "ID do usuário a matricular", example = "10")
        @NotNull(message = "Informe o usuário.")
        Long userId,

        @Schema(description = "ID do plano", example = "1")
        @NotNull(message = "Informe o plano.")
        Integer planoId,

        @Schema(description = "ID do personal (opcional)", example = "2")
        Long personalId,

        @Schema(description = "Objetivo do aluno", example = "Emagrecimento")
        String objetivo
) {}
