package com.ages.volunteersmile.adapter.controller.calendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ages.volunteersmile.application.dto.RoomAvailableDTO;
import com.ages.volunteersmile.application.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {

    private final RoomService roomService;

    public CalendarController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Lista quartos disponíveis na data informada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de quartos disponíveis retornada"),
            @ApiResponse(responseCode = "400", description = "Data inválida")
    })
    @GetMapping(value = "/rooms/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoomAvailableDTO>> roomsAvailableInDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(roomService.listAvailableByDate(date));
    }
}
