package com.ages.volunteersmile.adapter.controller.volunteer;

import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final UserApplicationService volunteerService;

    @Autowired
    public VolunteerController(UserApplicationService volunteerService) {
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
    public ResponseEntity<VolunteerDTO> createVolunteer(@RequestBody CreateVolunteerDTO body) {
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

    @Operation(summary = "Atualizar voluntário", description = "Atualiza um voluntário existente pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voluntário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@PathVariable UUID id, @RequestBody VolunteerDTO body) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(id, body));
    }

    @Operation(summary = "Atualizar senha do voluntário", description = "Atualiza a senha de um voluntário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
    })
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody UpdatePasswordDTO body) {
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
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }
}
