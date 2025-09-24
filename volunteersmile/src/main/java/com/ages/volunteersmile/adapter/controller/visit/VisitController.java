package com.ages.volunteersmile.adapter.controller.visit;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ages.volunteersmile.application.dto.CreateVisitDTO;
import com.ages.volunteersmile.application.dto.VisitDTO;
import com.ages.volunteersmile.application.service.VisitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/visits")
@CrossOrigin(origins = "*")
public class VisitController {

	private final VisitService visitService;

	public VisitController(VisitService visitService) {
		this.visitService = visitService;
	}

	@Operation(summary = "Cria uma nova visita", description = "Cria uma visita já vinculando um voluntário responsável (obrigatório)")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Visita criada"),
			@ApiResponse(responseCode = "400", description = "Erro de validação"),
			@ApiResponse(responseCode = "404", description = "Quarto ou voluntário não encontrado")
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VisitDTO> create(@Valid @RequestBody CreateVisitDTO request) {
		VisitDTO created = visitService.createVisit(request);
		return ResponseEntity.created(URI.create("/visits/" + created.getId())).body(created);
	}

	@Operation(summary = "Lista todas as visitas")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Lista retornada")
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VisitDTO>> listAll() {
		return ResponseEntity.ok(visitService.listAll());
	}

     @Operation(summary = "Busca a próxima visita do voluntário",
            description = "Retorna a próxima visita agendada para o voluntário especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Próxima visita encontrada"),
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado ou não possui próximas visitas")
    })
    @GetMapping(value = "/volunteer/{volunteerId}/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitDTO> getNextVisitByVolunteer(@PathVariable("volunteerId") UUID volunteerId) {
        VisitDTO nextVisit = visitService.getNextVisitByVolunteer(volunteerId);
        return ResponseEntity.ok(nextVisit);
    }
}
