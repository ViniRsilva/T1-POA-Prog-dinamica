package com.ages.volunteersmile.adapter.controller.visit;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@Operation(summary = "Lista visitas que ocorrem no dia informado")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Lista retornada") })
	@GetMapping(value = "/day", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VisitDTO>> listByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(visitService.listByDay(date));
	}

	@Operation(summary = "Lista visitas que ocorrem no mês da data informada")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Lista retornada") })
	@GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VisitDTO>> listByMonth(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateInMonth) {
		return ResponseEntity.ok(visitService.listByMonth(dateInMonth));
	}
}
