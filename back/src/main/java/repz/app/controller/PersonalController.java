package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repz.app.dto.request.PersonalCreateRequest;
import repz.app.dto.request.PersonalUpdateRequest;
import repz.app.dto.response.PersonalAlunosResponse;
import repz.app.dto.response.PersonalResponse;

import java.util.List;

@RequestMapping("/personais")
@Tag(name = "Personais", description = "Gerenciar personais e instrutores")
public interface PersonalController {

    @PostMapping
    @Operation(summary = "Criar personal", description = "Cadastrar novo personal vinculado à academia")
    @ApiResponse(responseCode = "200", description = "Personal criado com sucesso")
    PersonalResponse criarPersonal(@Valid @RequestBody PersonalCreateRequest request, Authentication auth);

    @GetMapping
    @Operation(summary = "Listar personais", description = "Listar personais (ADMIN vê todos, ACADEMIA vê apenas da sua unidade)")
    @ApiResponse(responseCode = "200", description = "Lista de personais")
    List<PersonalResponse> listarPersonais(Authentication auth);

    @GetMapping("/{id}")
    @Operation(summary = "Obter personal", description = "Buscar personal por ID")
    @ApiResponse(responseCode = "200", description = "Personal encontrado")
    PersonalResponse obterPorId(@Parameter(description = "ID do personal") @PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Editar personal", description = "Editar dados do personal (especialidade, status)")
    @ApiResponse(responseCode = "200", description = "Personal atualizado")
    PersonalResponse atualizarPersonal(
            @Parameter(description = "ID do personal") @PathVariable Long id,
            @Valid @RequestBody PersonalUpdateRequest request,
            Authentication auth);

    @PatchMapping("/{id}")
    @Operation(summary = "Inativar personal", description = "Inativar personal (soft delete)")
    @ApiResponse(responseCode = "200", description = "Personal inativado")
    PersonalResponse inativarPersonal(@Parameter(description = "ID do personal") @PathVariable Long id, Authentication auth);

    @GetMapping("/me")
    @Operation(summary = "Meu perfil", description = "Ver dados do perfil do personal autenticado")
    @ApiResponse(responseCode = "200", description = "Dados do personal")
    PersonalResponse obterMeuPerfil(Authentication auth);

    @PutMapping("/me")
    @Operation(summary = "Editar meu perfil", description = "Editar dados do próprio perfil")
    @ApiResponse(responseCode = "200", description = "Perfil atualizado")
    PersonalResponse atualizarMeuPerfil(@Valid @RequestBody PersonalUpdateRequest request, Authentication auth);

    @GetMapping("/me/alunos")
    @Operation(summary = "Meus alunos", description = "Painel com lista de alunos vinculados ao personal")
    @ApiResponse(responseCode = "200", description = "Lista de alunos do personal")
    PersonalAlunosResponse obterMeusAlunos(Authentication auth);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar personal", description = "Remover personal do sistema")
    @ApiResponse(responseCode = "200", description = "Personal deletado")
    void deletar(@Parameter(description = "ID do personal") @PathVariable Long id);
}


