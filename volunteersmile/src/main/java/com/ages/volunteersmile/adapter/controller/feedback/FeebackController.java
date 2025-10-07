package com.ages.volunteersmile.adapter.controller.feedback;

import com.ages.volunteersmile.application.service.VisitService;
import com.ages.volunteersmile.application.dto.UserFeedbackDTO;
import com.ages.volunteersmile.application.dto.FeedbackDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/feedback")
public class FeebackController {
    private final VisitService visitService;

    public FeebackController(VisitService visitService) {
        this.visitService = visitService;
    }

    @Operation(summary = "Adiciona feedback do voluntário à última visita do usuário",
            description = "Recebe o userId e feedback")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Feedback adicionado com sucesso"),
        @ApiResponse(responseCode = "404", description = "UserVisit não encontrado para o usuário informado")
    })
    @PatchMapping()
    public ResponseEntity<FeedbackDTO> addVolunteerFeedback(@Valid @RequestBody UserFeedbackDTO dto) {
        FeedbackDTO response = visitService.addVolunteerFeedback(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtém o último feedback de voluntário para um quarto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Feedback retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado para o quarto")
    })
    @GetMapping("/last")
    public ResponseEntity<FeedbackDTO> getLastVolunteerFeedback(@RequestParam UUID roomId) {
        FeedbackDTO feedback = visitService.getLastVolunteerFeedback(roomId);
        return ResponseEntity.ok(feedback);
    }

    @Operation(summary = "Lista todos os feedbacks de voluntários para um quarto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de feedbacks retornada com sucesso")
    })
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksByRoom(@PathVariable UUID roomId) {
        List<FeedbackDTO> feedbacks = visitService.getAllFeedbacksByRoom(roomId);
        return ResponseEntity.ok(feedbacks);
    }
}
