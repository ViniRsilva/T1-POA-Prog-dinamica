package com.ages.volunteersmile.adapter.controller.room;

import com.ages.volunteersmile.application.dto.CreateRoomDTO;
import com.ages.volunteersmile.application.dto.RoomDTO;
import com.ages.volunteersmile.application.dto.UpdateRoomDTO;
import com.ages.volunteersmile.application.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @Operation(summary = "Cria um novo quarto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Quarto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Número de quarto duplicado")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> create(@Valid @RequestBody CreateRoomDTO request) {
        RoomDTO created = service.addRoom(request);
        return ResponseEntity
                .created(URI.create("/rooms/" + created.getNumber()))
                .body(created);
    }

    @Operation(summary = "Lista todos os quartos")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoomDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca um quarto por ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @Operation(summary = "Atualiza um quarto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quarto atualizado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateRoomDTO request) {
        RoomDTO updated = service.updateRoom(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta um quarto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Quarto deletado"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}