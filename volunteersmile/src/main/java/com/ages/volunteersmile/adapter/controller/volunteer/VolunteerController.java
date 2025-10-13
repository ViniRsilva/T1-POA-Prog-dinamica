package com.ages.volunteersmile.adapter.controller.volunteer;

import java.util.List;
import java.util.UUID;


import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerProfileDTO;
import com.ages.volunteersmile.application.dto.UpdateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ages.volunteersmile.application.service.VolunteerApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerApplicationService volunteerService;

    @Autowired
    public VolunteerController(VolunteerApplicationService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Criar voluntário", description = "Cria um novo voluntário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voluntário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<VolunteerDTO> createVolunteer(@Valid @RequestBody CreateVolunteerDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(volunteerService.createVolunteer(body));
    }

    @Operation(summary = "Buscar voluntário por ID", description = "Retorna um voluntário pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voluntário encontrado"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDTO> getVolunteerById(@PathVariable UUID id){
        return ResponseEntity.ok(volunteerService.findById(id));
    }

    @Operation(summary = "Buscar perfil de voluntário por ID", description = "Retorna o perfil do voluntário pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voluntário encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = VolunteerProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado",
            content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/profile/{id}")
    public ResponseEntity<VolunteerProfileDTO> getVolunteerProfileById(@PathVariable UUID id) {
        return ResponseEntity.ok(volunteerService.getProfilebyID(id));
    }


    @Operation(summary = "Buscar voluntário por email", description = "Retorna um voluntário pelo seu email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voluntário encontrado"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<VolunteerDTO> getVolunteerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(volunteerService.findByEmail(email));
    }

    @Operation(summary = "Listar todos os voluntários", description = "Retorna todos os voluntários ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de voluntários retornada")
    })
    @GetMapping
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.findAll());
    }

    @Operation(summary = "Listar voluntários paginados", description = "Retorna uma lista paginada de voluntários com controle de página e tamanho")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada de voluntários retornada com sucesso")
    })
    @GetMapping("/paginated")
    public ResponseEntity<Page<VolunteerDTO>> getAllVolunteersPaginated(
            @Parameter(description = "Número da página (começa em 0)", schema = @Schema(type = "integer", defaultValue = "0", minimum = "0"))
            @RequestParam(defaultValue = "0") int pageNumber,
            @Parameter(description = "Quantidade de itens por página", schema = @Schema(type = "integer", defaultValue = "10", minimum = "1", maximum = "100"))
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<VolunteerDTO> volunteers = volunteerService.findAllPaginated(pageNumber, pageSize);
        return ResponseEntity.ok(volunteers);
    }

    @Operation(summary = "Atualizar voluntário", description = "Atualiza um voluntário existente pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voluntário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@PathVariable UUID id, @Valid @RequestBody UpdateVolunteerDTO body) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(id, body));
    }

    @Operation(summary = "Atualizar senha do voluntário", description = "Atualiza a senha de um voluntário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
    })
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @Valid @RequestBody UpdatePasswordDTO body) {
        volunteerService.updatePassword(id, body);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar voluntário", description = "Deleta um voluntário pelo seu ID (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Voluntário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable UUID id) {
        volunteerService.deleteVolunteerById(id);
        return ResponseEntity.noContent().build();
    }
}
