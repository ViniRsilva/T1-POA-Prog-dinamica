package com.ages.volunteersmile.adapter.controller.feedback;

import com.ages.volunteersmile.application.service.VisitService;
import com.ages.volunteersmile.application.dto.UserVisitFeedbackDTO;
import com.ages.volunteersmile.application.dto.FeedbackDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/feedback")
public class FeebackController {
    private final VisitService visitService;

    public FeebackController(VisitService visitService) {
        this.visitService = visitService;
    }

    @Operation(summary = "Adicionar feedback do voluntário", description = "Adiciona feedback do voluntário para a última visita do usuário informado (envie userId e feedback no corpo)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedback registrado com sucesso", content = @Content(schema = @Schema(implementation = FeedbackDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "UserVisit não encontrado para o usuário")
    })
    @PatchMapping()
    public ResponseEntity<FeedbackDTO> addVolunteerFeedback(@Valid @RequestBody UserVisitFeedbackDTO dto) {
        FeedbackDTO response = visitService.addVolunteerFeedback(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Último feedback por quarto", description = "Retorna o último feedback existente para um quarto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Último feedback retornado", content = @Content(schema = @Schema(implementation = FeedbackDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado para o quarto")
    })
    @GetMapping("/last")
    public ResponseEntity<FeedbackDTO> getLastVolunteerFeedback(@Parameter(description = "ID do quarto", required = true) @RequestParam UUID roomId) {
        FeedbackDTO feedback = visitService.getLastVolunteerFeedback(roomId);
        return ResponseEntity.ok(feedback);
    }

    @Operation(summary = "Todos os feedbacks por quarto", description = "Retorna todos os feedbacks existentes para um quarto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de feedbacks retornada", content = @Content(array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = FeedbackDTO.class))))
    })
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksByRoom(@Parameter(description = "ID do quarto", required = true) @PathVariable UUID roomId) {
        List<FeedbackDTO> feedbacks = visitService.getAllFeedbacksByRoom(roomId);
        return ResponseEntity.ok(feedbacks);
    }
}
