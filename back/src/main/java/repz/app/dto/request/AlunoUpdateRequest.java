package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de matrícula do aluno")
public record AlunoUpdateRequest(

        @Schema(description = "ID do novo plano", example = "2")
        Integer planoId,

        @Schema(description = "ID do novo personal", example = "3")
        Long personalId,

        @Schema(description = "Objetivo do aluno", example = "Ganho de massa")
        String objetivo
) {}
